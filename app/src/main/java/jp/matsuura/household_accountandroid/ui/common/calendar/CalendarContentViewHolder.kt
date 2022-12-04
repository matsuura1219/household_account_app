package jp.matsuura.household_accountandroid.ui.common.calendar

import jp.matsuura.household_accountandroid.ui.common.BaseViewHolder
import jp.matsuura.householda_ccountandroid.databinding.ItemCalendarContentsBinding

class CalendarContentViewHolder(private val binding: ItemCalendarContentsBinding, val onItemClicked: ((Int, Int, Int) -> Unit)): BaseViewHolder(binding.root) {

    fun bind(item: CalendarView.CalendarItem.Day) {
        binding.day.text = item.day.toString()
        binding.totalMoney.text = if (item.totalMoney != null && item.totalMoney != 0) item.totalMoney.toString() else ""
        binding.root.setOnClickListener {
            if (item.isCurrentMonth) {
                onItemClicked(item.year, item.month, item.day)
            }
        }
    }
}