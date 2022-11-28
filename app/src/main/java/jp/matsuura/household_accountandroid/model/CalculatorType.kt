package jp.matsuura.household_accountandroid.model

sealed class CalculatorType {
    data class Number(val number: Int): CalculatorType()
    data class Signal(val signal: String): CalculatorType()
}