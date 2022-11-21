package jp.matsuura.household_accountandroid.ext

fun String.isNotNumber(): Boolean {
    return try {
        this.toInt()
        false
    } catch (e: NumberFormatException) {
        true
    }
}