package jp.matsuura.household_accountandroid.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import jp.matsuura.household_accountandroid.data.db.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getAll(): List<CategoryEntity>

    @Insert
    fun insert(entity: CategoryEntity)

    @Delete
    fun delete(entity: CategoryEntity)
}