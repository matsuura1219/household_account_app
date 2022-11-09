package jp.matsuura.household_accountandroid.ui.input_money

data class InputMoneyScreenState(
    val year: Int,
    val month: Int,
    val day: Int,
    val dayOfWeek: String,
    val time: String,
    val categoryName: String,
    val totalMoney: Int,
    val isNewData: Boolean,
) {
    companion object {
        val initValue = InputMoneyScreenState(
            year = -1,
            month = -1,
            day = -1,
            dayOfWeek = "",
            time = "",
            categoryName = "",
            totalMoney = -1,
            isNewData = false,
        )
    }
}