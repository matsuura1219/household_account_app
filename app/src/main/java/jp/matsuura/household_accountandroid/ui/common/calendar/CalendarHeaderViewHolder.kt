package jp.matsuura.household_accountandroid.ui.common.calendar

import jp.matsuura.household_accountandroid.ui.common.BaseViewHolder
import jp.matsuura.householda_ccountandroid.databinding.ItemCalendarHeaderBinding

class CalendarHeaderViewHolder(private val binding: ItemCalendarHeaderBinding): BaseViewHolder(binding.root) {

    fun bind(week: String) {
        binding.youbi.text = week
    }

}