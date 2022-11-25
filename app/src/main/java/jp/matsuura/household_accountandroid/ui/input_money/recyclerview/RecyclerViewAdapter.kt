package jp.matsuura.household_accountandroid.ui.input_money.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import jp.matsuura.householda_ccountandroid.R

class RecyclerViewAdapter(private val dataSet: List<String>) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.categoryTextView.text = dataSet[position]
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}