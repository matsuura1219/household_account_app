package jp.matsuura.household_accountandroid.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.matsuura.household_accountandroid.domain.GetTransactionByDayUseCase
import jp.matsuura.household_accountandroid.domain.GetTransactionByMonthUseCase
import jp.matsuura.household_accountandroid.model.TransactionModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getTransactionByMonth: GetTransactionByMonthUseCase,
    private val getTransactionByDay: GetTransactionByDayUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            isProgressBarVisible = false,
            currentYear = -1,
            currentMonth = -1,
            currentDay = -1,
            transactionListByMonth = emptyList(),
            transactionListByDays = emptyList()
        )
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    init {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DATE) + 1
        _uiState.update { it.copy(currentYear = year, currentMonth = month, currentDay = day) }

        viewModelScope.launch {
            kotlin.runCatching {
                getTransactionByMonth(year = year, month = month)
            }.onSuccess { transactions ->
                _uiState.update { it.copy(transactionListByMonth = transactions) }
            }.onFailure {
                Timber.d(it)
                _uiEvent.emit(UiEvent.Failure)
            }
        }
    }

    fun onDayClicked(year: Int, month: Int, day: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                getTransactionByDay(year = year, month = month, day = day)
            }.onSuccess { transactions ->
                _uiState.update { it.copy(transactionListByDays = transactions) }
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
                getTransactionByMonth(year = currentYear, month = currentMonth)
            }.onSuccess { transactions ->
                _uiState.update {
                    it.copy(
                        transactionListByMonth = transactions,
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
                getTransactionByMonth(year = currentYear, month = currentMonth)
            }.onSuccess { transactions ->
                _uiState.update {
                    it.copy(
                        transactionListByMonth = transactions,
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
        val transactionListByMonth: List<TransactionModel>,
        val transactionListByDays: List<TransactionModel>
    )

    sealed interface UiEvent {
        object Success : UiEvent
        object Failure : UiEvent
    }

}