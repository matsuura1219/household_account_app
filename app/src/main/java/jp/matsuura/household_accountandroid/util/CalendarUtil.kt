package jp.matsuura.household_accountandroid.util

import jp.matsuura.household_accountandroid.model.WeekType

object CalendarUtil {

    private fun isLeapYear(year: Int): Boolean {
        return (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
    }

    fun dayOfWeek(year: Int, month: Int, day: Int): WeekType {
        var year = year
        var month = month
        if (month == 1 || month == 2) {
            year -= 1
            month += 12
        }

        // refer to: https://ja.wikipedia.org/wiki/%E3%83%84%E3%82%A7%E3%83%A9%E3%83%BC%E3%81%AE%E5%85%AC%E5%BC%8F
        val value = (year + year / 4 - year / 100 + year / 400 + (13 * month + 8) /5 + day) % 7
        return when (value) {
            0 -> WeekType.SUN
            1 -> WeekType.MON
            2 -> WeekType.TUE
            3 -> WeekType.WED
            4 -> WeekType.THU
            5 -> WeekType.FRI
            6 -> WeekType.SAT
            else -> throw IllegalStateException("wrong value!!")
        }
    }

    private fun hasFourWeeks(year: Int, month: Int): Boolean {
        val firstDayOfWeek = dayOfWeek(year, month, 1)
        return !isLeapYear(year) && month == 2 && firstDayOfWeek == WeekType.SUN
    }

    private fun hasSixWeeks(year: Int, month: Int): Boolean {
        val firstDayOfWeek = dayOfWeek(year, month, 1)
        val days = numberOfDays(year, month)
        return if (firstDayOfWeek == WeekType.SAT && days == 30) {
            true
        } else (firstDayOfWeek == WeekType.FRI || firstDayOfWeek == WeekType.SAT) && days == 31
    }

    private fun numberOfWeeks(year: Int, month: Int): Int {
        return if (hasFourWeeks(year, month)) {
            4
        } else if (hasSixWeeks(year, month)) {
            6
        } else {
            5
        }
    }

    fun numberOfDays(year: Int, month: Int): Int {
        val monthMaxDay = mutableMapOf(
            1 to 31,
            2 to 28,
            3 to 31,
            4 to 30,
            5 to 31,
            6 to 30,
            7 to 31,
            8 to 31,
            9 to 30,
            10 to 31,
            11 to 30,
            12 to 31,
        )
        if (month == 2 && isLeapYear(year)) {
            monthMaxDay[2] = 29
        }
        return monthMaxDay[month] ?: throw IllegalStateException("wrong value!")
    }

}