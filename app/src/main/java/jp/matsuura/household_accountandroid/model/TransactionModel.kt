package jp.matsuura.household_accountandroid.model

import java.util.*

data class TransactionModel(
    val categoryId: Int,
    val money: Int,
    val createdAt: Calendar,
    val updatedAt: Calendar,
)
