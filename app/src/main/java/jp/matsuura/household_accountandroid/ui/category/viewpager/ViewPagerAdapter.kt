package jp.matsuura.household_accountandroid.ui.category.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import jp.matsuura.household_accountandroid.ui.category.viewpager.CategoryFragment

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment = CategoryFragment.newInstance(position = position)
}