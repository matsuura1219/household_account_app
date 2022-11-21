package jp.matsuura.household_accountandroid.ui.input_money

sealed interface InputMoneyScreenEvent {
    object Success: InputMoneyScreenEvent
    data class Failure(val e: Throwable): InputMoneyScreenEvent
}