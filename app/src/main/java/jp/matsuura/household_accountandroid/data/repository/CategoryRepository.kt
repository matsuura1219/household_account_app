package jp.matsuura.household_accountandroid.data.repository

import jp.matsuura.household_accountandroid.data.converter.toModel
import jp.matsuura.household_accountandroid.data.db.AppDatabase
import jp.matsuura.household_accountandroid.data.db.entity.CategoryEntity
import jp.matsuura.household_accountandroid.model.CategoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val db: AppDatabase,
) {

    suspend fun getAll(): List<CategoryModel> {
        return withContext(Dispatchers.IO) {
            db.categoryDao().getAll().map { it.toModel() }
        }
    }

    suspend fun insert(category: CategoryModel) {
        return withContext(Dispatchers.IO) {
            val entity = CategoryEntity(
                id = category.id,
                categoryName = category.categoryName,
                iconColor = category.iconColor,
            )
            db.categoryDao().insert(entity = entity)
        }
    }

    suspend fun delete(category: CategoryModel) {
        return withContext(Dispatchers.IO) {
            val entity = CategoryEntity(
                id = category.id,
                categoryName = category.categoryName,
                iconColor = category.iconColor,
            )
            db.categoryDao().delete(entity = entity)
        }
    }

}