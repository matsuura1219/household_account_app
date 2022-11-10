package jp.matsuura.household_accountandroid.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jp.matsuura.household_accountandroid.data.db.dao.CategoryDao
import jp.matsuura.household_accountandroid.data.db.dao.TransactionDao
import jp.matsuura.household_accountandroid.data.db.entity.CategoryEntity
import jp.matsuura.household_accountandroid.data.db.entity.TransactionEntity

@Database(
    entities = [
        CategoryEntity::class,
        TransactionEntity::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
}