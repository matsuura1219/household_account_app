package jp.matsuura.household_accountandroid.ui.common.switch_view

data class State @JvmOverloads constructor(
    val text: String,
    val selectedText: String = text,
    val disabledText: String = text
)