package jp.matsuura.household_accountandroid.ui.input_money

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.matsuura.household_accountandroid.model.CalculatorType
import kotlinx.coroutines.flow.*
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class InputMoneyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val args = InputMoneyFragmentArgs.fromSavedStateHandle(savedStateHandle = savedStateHandle)
    private val categoryId: Int = args.categoryId
    private val categoryName: String = args.categoryName

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            isProgressVisible = false,
            categoryName = categoryName,
            totalMoney = 0,
            currentDate = Date(),
        )
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    data class UiState(
        val isProgressVisible: Boolean,
        val categoryName: String,
        val totalMoney: Int,
        val currentDate: Date,
    )

    sealed interface UiEvent {
        object Success : UiEvent
        object Failure : UiEvent
    }
}