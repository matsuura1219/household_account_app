package jp.matsuura.household_accountandroid.ui.input_money

import jp.matsuura.household_accountandroid.model.CategoryModel
import java.util.Date

data class InputMoneyScreenState(
    val currentTime: Date,
    val category: CategoryModel,
    val totalMoney: Int,
    val isNewData: Boolean,
    val isProgressBar: Boolean,
) {
    companion object {
        val initValue = InputMoneyScreenState(
            currentTime = Date(),
            category = CategoryModel(id = -1, categoryName = ""),
            totalMoney = -1,
            isNewData = false,
            isProgressBar = false,
        )
    }
}