package jp.matsuura.household_accountandroid.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.matsuura.household_accountandroid.data.db.dao.CategoryDao
import jp.matsuura.household_accountandroid.data.db.entity.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}