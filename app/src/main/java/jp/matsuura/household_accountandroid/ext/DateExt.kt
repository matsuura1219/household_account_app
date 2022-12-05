package jp.matsuura.household_accountandroid.ext

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date.toStringDateForApp(): String {
    return SimpleDateFormat("yyyy年MM月dd日").format(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.toStringTimeForApp(): String {
    return SimpleDateFormat("hh時mm分").format(this)
}