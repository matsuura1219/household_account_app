package jp.matsuura.household_accountandroid.ui.category.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.matsuura.householda_ccountandroid.R

class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val rootView: View
    val categoryImageView: ImageView
    val categoryTextView: TextView

    init {
        rootView = view.findViewById(R.id.container)
        categoryImageView = view.findViewById(R.id.categoryImageView)
        categoryTextView = view.findViewById(R.id.categoryTextView)
    }
}