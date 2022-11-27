package jp.matsuura.household_accountandroid.data.converter

import jp.matsuura.household_accountandroid.data.db.entity.CategoryEntity
import jp.matsuura.household_accountandroid.model.CategoryModel

fun CategoryEntity.toModel(): CategoryModel {
    return CategoryModel(
        id = id,
        categoryName = categoryName,
        categoryType = categoryType,
    )
}