package jp.matsuura.household_accountandroid.ui.input_money

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.matsuura.household_accountandroid.domain.GetAllCategoryUseCase
import jp.matsuura.household_accountandroid.domain.InsertTransactionUseCase
import jp.matsuura.household_accountandroid.model.CalculatorType
import jp.matsuura.household_accountandroid.model.CategoryModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date
import java.util.SimpleTimeZone
import javax.inject.Inject

@HiltViewModel
class InputMoneyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val insertTransaction: InsertTransactionUseCase,
    private val getAllCategory: GetAllCategoryUseCase,
) : ViewModel() {

    private val args = InputMoneyFragmentArgs.fromSavedStateHandle(savedStateHandle = savedStateHandle)
    private val categoryId: Int = args.categoryId
    private val categoryName: String = args.categoryName

    private var currentCategoryId: Int = categoryId

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            isProgressVisible = false,
            categoryName = categoryName,
            totalMoney = 0,
            currentDate = Date(),
            categoryItemList = emptyList(),
        )
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                _uiState.update { it.copy(isProgressVisible = true) }
                getAllCategory()
            }.onSuccess { categoryList ->
                _uiState.update { it.copy(categoryItemList = categoryList) }
            }.onFailure {
                Timber.d(it)
                _uiEvent.emit(UiEvent.Failure)
            }
            _uiState.update { it.copy(isProgressVisible = false) }
        }
    }

    fun onCalculatorClicked(value: CalculatorType) {
        when (value) {
            is CalculatorType.Number -> {
                handleNumber(number = value.number)
            }
            is CalculatorType.Signal -> {
                handleSignal(signal = value.signal)
            }
        }
    }

    fun onItemSelected(itemName: String) {
        val categoryList = _uiState.value.categoryItemList
        currentCategoryId = categoryList.first { it.categoryName == itemName }.id
    }

    fun onDateChanged(time: Long) {
        _uiState.update { it.copy(currentDate = Date(time)) }
    }

    fun onConfirmButtonClicked() {
        val currentTotalMoney: Int = _uiState.value.totalMoney
        viewModelScope.launch {
            _uiState.update { it.copy(isProgressVisible = true) }
            if (currentTotalMoney == 0) {
                _uiEvent.emit(UiEvent.NotInputMoney)
                return@launch
            }
            kotlin.runCatching {
                insertTransaction(
                    categoryId = categoryId,
                    money = _uiState.value.totalMoney,
                    currentTime = _uiState.value.currentDate
                )
            }.onSuccess {
                _uiEvent.emit(UiEvent.Success(categoryName = categoryName, totalMoney = _uiState.value.totalMoney))
            }.onFailure {
                Timber.d(it)
                _uiEvent.emit(UiEvent.Failure)
            }
            _uiState.update { it.copy(isProgressVisible = false) }
        }
    }

    private fun handleNumber(number: Int) {
        if (_uiState.value.totalMoney == 0) {
            _uiState.update { it.copy(totalMoney = number) }
        } else {
            val currentTotalMoney: String = _uiState.value.totalMoney.toString()
            if (currentTotalMoney.length >= MAX_TOTAL_ACCOUNT_DIGITS) {
                return
            }
            val result: String = currentTotalMoney + number.toString()
            _uiState.update { it.copy(totalMoney = result.toInt()) }
        }
    }

    private fun handleSignal(signal: String) {
        when (signal) {
            "DEL" -> {
                val currentTotalMoney = _uiState.value.totalMoney
                _uiState.update { it.copy(totalMoney = currentTotalMoney / 10) }
            }
        }
    }

    data class UiState(
        val isProgressVisible: Boolean,
        val categoryName: String,
        val totalMoney: Int,
        val currentDate: Date,
        val categoryItemList: List<CategoryModel>
    )

    sealed interface UiEvent {
        data class Success(val categoryName: String, val totalMoney: Int) : UiEvent
        object Failure : UiEvent
        object NotInputMoney : UiEvent
    }

    companion object {
        const val MAX_TOTAL_ACCOUNT_DIGITS: Int = 8
    }
}