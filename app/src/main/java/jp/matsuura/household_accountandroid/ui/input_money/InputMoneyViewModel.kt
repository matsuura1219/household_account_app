package jp.matsuura.household_accountandroid.ui.input_money

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.matsuura.household_accountandroid.ext.isNotNumber
import jp.matsuura.household_accountandroid.model.CalculatorSymbolType
import jp.matsuura.household_accountandroid.model.CategoryModel
import jp.matsuura.household_accountandroid.usecase.InsertTransactionDataUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InputMoneyViewModel @Inject constructor(
    private val insertTransactionData: InsertTransactionDataUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        InputMoneyScreenState.initValue
    )
    val uiState: StateFlow<InputMoneyScreenState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<InputMoneyScreenEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        val categoryId: Int? = savedStateHandle.get<String>("categoryId")?.toInt()
        val categoryName: String? = savedStateHandle.get<String>("categoryName")
        if (categoryId != null && categoryName != null) {
            _uiState.update { it.copy(
                category = CategoryModel(
                    id = categoryId,
                    categoryName = categoryName,
                )
            )
            }
        }
    }

    fun onFinishButtonClicked() {
        viewModelScope.launch {
            kotlin.runCatching {
                _uiState.update { it.copy(isProgressBar = true) }
                insertTransactionData(
                    categoryId = uiState.value.category.id,
                    moneyMount = uiState.value.totalMoney,
                    currentTime = uiState.value.currentTime,
                )
            }.onSuccess {
                _uiEvent.emit(InputMoneyScreenEvent.Success)
            }.onFailure {
                Timber.d(it)
                _uiEvent.emit(InputMoneyScreenEvent.Failure(it))
            }
            _uiState.update { it.copy(isProgressBar = false) }
        }
    }

    fun onNumberClick(input: String) {
        if (input.isBlank() && !input.isNotNumber()) return
        val number = uiState.value.totalMoney
        val result: String = if (number == 0) {
            input
        } else {
            number.toString() + input
        }
        _uiState.update { it.copy( totalMoney = result.toInt() ) }
    }

    fun onSymbolClick(type: CalculatorSymbolType) {
        when (type) {
            CalculatorSymbolType.DEL -> {
                val number = uiState.value.totalMoney / 10
                _uiState.update { it.copy( totalMoney = number ) }
            }
            else -> {}
        }
    }



}