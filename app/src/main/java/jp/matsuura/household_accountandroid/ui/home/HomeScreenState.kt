package jp.matsuura.household_accountandroid.ui.home

import jp.matsuura.household_accountandroid.model.CategoryModel

data class HomeScreenState(
    val isProgressBar: Boolean,
    val categories: List<CategoryModel>,
) {
    companion object {
        val initValue = HomeScreenState(
            isProgressBar = false,
            categories = emptyList(),
        )
    }
}