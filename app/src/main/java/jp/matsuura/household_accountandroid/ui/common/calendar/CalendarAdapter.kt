package jp.matsuura.household_accountandroid.ui.common.calendar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.matsuura.household_accountandroid.ext.dpToPx
import jp.matsuura.household_accountandroid.ui.common.BaseViewHolder
import jp.matsuura.householda_ccountandroid.databinding.ItemCalendarContentsBinding
import jp.matsuura.householda_ccountandroid.databinding.ItemCalendarHeaderBinding

class CalendarAdapter(val onItemClicked: ((Int, Int, Int) -> Unit)): RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_DAY = 1
    }

    private var dataSource: List<CalendarView.CalendarItem> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun update(items: List<CalendarView.CalendarItem>) {
        if (items == dataSource) return
        dataSource = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val context = parent.context
        val bottomHeight = 100.dpToPx(context)
        val headerHeight = 40.dpToPx(context)
        val height = (parent.height - bottomHeight - headerHeight) / 6
        return when(viewType) {
            VIEW_TYPE_HEADER -> CalendarHeaderViewHolder(
                ItemCalendarHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            VIEW_TYPE_DAY -> CalendarContentViewHolder(
                ItemCalendarContentsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ).apply {
                    root.minHeight = height.toInt()
                },
                onItemClicked = onItemClicked
            )
            else -> throw IllegalStateException("Bad view type!!")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (val item = dataSource[position]) {
            is CalendarView.CalendarItem.Header -> {
                (holder as CalendarHeaderViewHolder).bind(item.week)
            }
            is CalendarView.CalendarItem.Day -> {
                (holder as CalendarContentViewHolder).bind(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return 7 * 7
    }

    override fun getItemViewType(position: Int): Int {
        return when(dataSource[position]) {
            is CalendarView.CalendarItem.Header -> VIEW_TYPE_HEADER
            is CalendarView.CalendarItem.Day -> VIEW_TYPE_DAY
        }
    }

}