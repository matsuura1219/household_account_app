package jp.matsuura.household_accountandroid.ext

import android.content.Context

fun Int.dpToPx(context: Context): Float {
    val metrics = context.resources.displayMetrics
    return this * metrics.density
}