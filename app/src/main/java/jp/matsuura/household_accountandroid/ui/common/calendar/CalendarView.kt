package jp.matsuura.household_accountandroid.ui.common.calendar

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.matsuura.household_accountandroid.model.TransactionModel
import jp.matsuura.household_accountandroid.model.WeekType
import jp.matsuura.household_accountandroid.util.CalendarUtil
import jp.matsuura.householda_ccountandroid.R
import java.util.*

typealias OnDayClicked = (Int, Int, Int) -> Unit

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
) : ConstraintLayout(context, attrs) {

    var onDayClicked: OnDayClicked? = null

    private val adapter: CalendarAdapter
    private val recyclerView: RecyclerView

    private val weekTypeArray = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    init {
        inflate(context, R.layout.item_calendar, this)
        adapter = CalendarAdapter { year, month, day ->
            onDayClicked?.invoke(year, month, day)
        }
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 7)

        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DATE)
        adapter.update(items = generateCalendar(year = currentYear, month = currentMonth, day = currentDay, transactionList = emptyList()))
    }

    fun update(year: Int, month: Int, transactionList: List<TransactionModel>) {
        val items = generateCalendar(year = year, month = month, day = 1, transactionList)
        adapter.update(items = items)
    }

    private fun generateCalendar(year: Int, month: Int, day: Int, transactionList: List<TransactionModel>): List<CalendarItem> {
        val calendarList = mutableListOf<CalendarItem>()
        weekTypeArray.forEach {
            calendarList.add(CalendarItem.Header(it))
        }
        val days = CalendarUtil.numberOfDays(
            year = year,
            month = month
        )
        val firstOfWeek = CalendarUtil.dayOfWeek(
            year = year,
            month = month,
            day = 1
        )
        val prevDays = firstOfWeek.value
        val prevMonthDays = if (month == 1) {
            CalendarUtil.numberOfDays(year = year - 1, month = 12)
        } else {
            CalendarUtil.numberOfDays(year = year, month = month - 1)
        }
        for (i in 1..prevDays) {
            calendarList.add(CalendarItem.Day(year = year, month = month, day = prevMonthDays - prevDays + i, totalMoney = null))
        }
        for (i in 1..days) {
            calendarList.add(
                CalendarItem.Day(
                    year = year,
                    month = month,
                    day = i,
                    isToDay = i == day,
                    isCurrentMonth = true,
                    weekType = CalendarUtil.dayOfWeek(year = year, month = month, day = i),
                    totalMoney = transactionList.filter { it.createdAt.get(Calendar.DATE) == i }.sumOf { it.money },
                )
            )
        }
        val nextDays = 42 - days - prevDays
        for (i in 1..nextDays) {
            calendarList.add(CalendarItem.Day(year = year, month = month, day = i, totalMoney = null))
        }
        return calendarList.toList()
    }

    sealed class CalendarItem {
        data class Header(val week: String) : CalendarItem()
        data class Day(
            val year: Int,
            val month: Int,
            val day: Int,
            val isToDay: Boolean = false,
            val isCurrentMonth: Boolean = false,
            val weekType: WeekType? = null,
            val totalMoney: Int?,
        ) : CalendarItem()
    }


}