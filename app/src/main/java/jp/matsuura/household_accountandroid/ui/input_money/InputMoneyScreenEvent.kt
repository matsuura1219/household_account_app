package jp.matsuura.household_accountandroid.ui.input_money

sealed interface InputMoneyScreenEvent {
    data class Success(val itemName: String, val moneyMount: Int): InputMoneyScreenEvent
    data class Failure(val e: Throwable): InputMoneyScreenEvent
}