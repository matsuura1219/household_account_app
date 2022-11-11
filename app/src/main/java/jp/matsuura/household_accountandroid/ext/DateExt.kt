package jp.matsuura.household_accountandroid.ext

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date.toStringForApp(): String {
    return SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(this)
}