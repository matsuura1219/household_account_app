package jp.matsuura.household_accountandroid.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
        HomeScreenState.initValue
    )
    val uiState: StateFlow<HomeScreenState> = _uiState.asStateFlow()

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

}