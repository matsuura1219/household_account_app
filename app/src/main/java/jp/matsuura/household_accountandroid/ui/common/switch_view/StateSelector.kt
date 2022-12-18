package jp.matsuura.household_accountandroid.ui.common.switch_view

import android.graphics.drawable.Drawable

/**
 * Stores the two representations of the state.
 */
data class StateSelector(
    val normal: Drawable,
    val selected: Drawable
)