package jp.matsuura.household_accountandroid.data.converter

import jp.matsuura.household_accountandroid.data.db.entity.CategoryEntity
import jp.matsuura.household_accountandroid.data.db.entity.TransactionEntity
import jp.matsuura.household_accountandroid.model.CategoryModel
import jp.matsuura.household_accountandroid.model.TransactionModel
import jp.matsuura.household_accountandroid.util.CalendarUtil
import java.util.*

fun CategoryEntity.toModel(): CategoryModel {
    return CategoryModel(
        id = id,
        categoryName = categoryName,
        categoryType = categoryType,
    )
}

fun TransactionEntity.toModel(): TransactionModel {
    val createdAt = Calendar.getInstance()
    createdAt.time = this.createdAt
    val updatedAt = Calendar.getInstance()
    updatedAt.time = this.updatedAt
    return TransactionModel(
        categoryId = categoryId,
        money = money,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}