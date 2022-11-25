package jp.matsuura.household_accountandroid.ui.input_money.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.matsuura.householda_ccountandroid.R

class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val categoryImageView: ImageView
    val categoryTextView: TextView

    init {
        categoryImageView = view.findViewById(R.id.categoryImageView)
        categoryTextView = view.findViewById(R.id.categoryTextView)
    }
}