package jp.matsuura.household_accountandroid.ext

import android.content.res.Resources

fun Float.dp2px(): Float {
    return this * Resources.getSystem().displayMetrics.density
}