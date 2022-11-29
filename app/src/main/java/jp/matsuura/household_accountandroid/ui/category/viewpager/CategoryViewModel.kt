package jp.matsuura.household_accountandroid.ui.category.viewpager

import androidx.lifecycle.SavedStateHandle
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
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState(
            isProgressVisible = false,
            categoryList = emptyList(),
        )
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val categoryType: Int = requireNotNull(savedStateHandle[CategoryFragment.POSITION])

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                _uiState.update { it.copy(isProgressVisible = true) }
                getAllCategory()
            }.onSuccess { categoryList ->
                _uiState.update {
                    it.copy(
                        isProgressVisible = false,
                        categoryList = categoryList.filter { category -> category.categoryType == categoryType },
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
        val categoryList: List<CategoryModel>,
    )

    sealed interface UiEvent {
        object Failure: UiEvent
    }
}