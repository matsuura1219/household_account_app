package jp.matsuura.household_accountandroid.ui.home

import android.app.appsearch.AppSearchSchema.BooleanPropertyConfig
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.matsuura.household_accountandroid.data.repository.CategoryRepository
import jp.matsuura.household_accountandroid.model.CategoryModel
import jp.matsuura.household_accountandroid.usecase.GetAllCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllCategory: GetAllCategoryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        UiState(
            isProgressBar = false,
            categories = emptyList(),
        )
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                _uiState.update { it.copy(isProgressBar = true) }
                getAllCategory()
            }.onSuccess { categories ->
                _uiState.update { it.copy(isProgressBar = false) }
                _uiState.update { it.copy(categories = categories) }
            }.onFailure {
                _uiState.update { it.copy(isProgressBar = false) }
            }
        }
    }

    data class UiState(
        val isProgressBar: Boolean,
        val categories: List<CategoryModel>,
    )
}