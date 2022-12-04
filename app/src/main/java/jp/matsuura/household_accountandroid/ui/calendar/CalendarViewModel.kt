package jp.matsuura.household_accountandroid.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.matsuura.household_accountandroid.domain.GetTransactionUseCase
import jp.matsuura.household_accountandroid.model.TransactionModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Thread.State
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getTransaction: GetTransactionUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            isProgressBarVisible = false,
            currentYear = -1,
            currentMonth = -1,
            currentDay = -1,
            transactionList = emptyList(),
        )
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    init {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DATE)
        _uiState.update { it.copy(currentYear = year, currentMonth = month, currentDay = day) }

        viewModelScope.launch {
            kotlin.runCatching {
                getTransaction(year = year, month = month)
            }.onSuccess { transactions ->
                _uiState.update { it.copy(transactionList = transactions) }
            }.onFailure {
                Timber.d(it)
                _uiEvent.emit(UiEvent.Failure)
            }
        }
    }

    fun onBackButtonClicked() {
        var currentYear = _uiState.value.currentYear
        var currentMonth = _uiState.value.currentMonth
        if (currentMonth == 1) {
            currentYear -= 1
            currentMonth = 12
        } else {
            currentMonth -= 1
        }
        viewModelScope.launch {
            kotlin.runCatching {
                getTransaction(year = currentYear, month = currentMonth)
            }.onSuccess { transactions ->
                _uiState.update {
                    it.copy(
                        transactionList = transactions,
                        currentYear = currentYear,
                        currentMonth = currentMonth,
                    )
                }
            }.onFailure {
                Timber.d(it)
                _uiEvent.emit(UiEvent.Failure)
            }
        }
    }

    fun onNextButtonClicked() {
        var currentYear = _uiState.value.currentYear
        var currentMonth = _uiState.value.currentMonth
        if (currentMonth == 12) {
            currentYear += 1
            currentMonth = 1
        } else {
            currentMonth += 1
        }
        viewModelScope.launch {
            kotlin.runCatching {
                getTransaction(year = currentYear, month = currentMonth)
            }.onSuccess { transactions ->
                _uiState.update {
                    it.copy(
                        transactionList = transactions,
                        currentYear = currentYear,
                        currentMonth = currentMonth,
                    )
                }
            }.onFailure {
                Timber.d(it)
                _uiEvent.emit(UiEvent.Failure)
            }
        }
    }

    data class UiState(
        val isProgressBarVisible: Boolean,
        val currentYear: Int,
        val currentMonth: Int,
        val currentDay: Int,
        val transactionList: List<TransactionModel>,
    )

    sealed interface UiEvent {
        object Success : UiEvent
        object Failure : UiEvent
    }

}