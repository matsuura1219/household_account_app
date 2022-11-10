package jp.matsuura.household_accountandroid.ui.input_money

sealed class InputMoneyScreenEvent {
    object Success: InputMoneyScreenEvent()
    object Failure: InputMoneyScreenEvent()
}