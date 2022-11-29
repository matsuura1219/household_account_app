package jp.matsuura.household_accountandroid.ui.category.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.matsuura.household_accountandroid.model.CategoryModel
import jp.matsuura.household_accountandroid.ui.category.recyclerview.CategoryViewHolder
import jp.matsuura.householda_ccountandroid.R

class RecyclerViewAdapter(
    private val onItemClicked: (CategoryModel) -> Unit,
) : RecyclerView.Adapter<CategoryViewHolder>() {

    private var dataSet: List<CategoryModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun update(items: List<CategoryModel>) {
        if (dataSet == items) return
        dataSet = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.rootView.setOnClickListener {
            onItemClicked(dataSet[position])
        }
        holder.categoryTextView.text = dataSet[position].categoryName
        holder.categoryImageView.setBackgroundResource(R.drawable.ic_food)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}