package jp.matsuura.household_accountandroid.ui.input_money.viewpager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.matsuura.household_accountandroid.domain.GetAllCategoryUseCase
import jp.matsuura.household_accountandroid.model.CategoryModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getAllCategory: GetAllCategoryUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            isProgressVisible = false,
            spendingCategoryList = emptyList(),
            incomeCategoryList = emptyList(),
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
                val spendingCategoryList = categoryList.filter { it.categoryType == 0 }.map { it.categoryName }
                val incomeCategoryList = categoryList.filter { it.categoryType == 1 }.map { it.categoryName }
                _uiState.update {
                    it.copy(
                        isProgressVisible = false,
                        spendingCategoryList = spendingCategoryList,
                        incomeCategoryList = incomeCategoryList,
                    )
                }
            }.onFailure {
                Timber.d(it)
                _uiEvent.emit(UiEvent.Failure)
            }
            _uiState.update { it.copy(isProgressVisible = false) }
        }
    }

    data class UiState(
        val isProgressVisible: Boolean,
        val spendingCategoryList: List<String>,
        val incomeCategoryList: List<String>,
    )

    sealed interface UiEvent {
        object Failure: UiEvent
    }
}