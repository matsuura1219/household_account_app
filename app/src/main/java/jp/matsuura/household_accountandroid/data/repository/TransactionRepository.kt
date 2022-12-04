package jp.matsuura.household_accountandroid.data.repository

import jp.matsuura.household_accountandroid.data.db.AppDatabase
import jp.matsuura.household_accountandroid.data.db.entity.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val db: AppDatabase,
) {

    suspend fun insertTransaction(categoryId: Int, money: Int, currentTime: Date) {
        return withContext(Dispatchers.IO) {
            val entity = TransactionEntity(
                id = 0,
                categoryId = categoryId,
                money = money,
                createdAt = currentTime,
                updatedAt = currentTime,
            )
            db.transactionDao().insert(entity = entity)
        }
    }
}