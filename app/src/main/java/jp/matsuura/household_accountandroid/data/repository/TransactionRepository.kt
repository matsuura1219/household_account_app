package jp.matsuura.household_accountandroid.data.repository

import jp.matsuura.household_accountandroid.data.converter.toModel
import jp.matsuura.household_accountandroid.data.db.AppDatabase
import jp.matsuura.household_accountandroid.data.db.entity.TransactionEntity
import jp.matsuura.household_accountandroid.model.TransactionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
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

    suspend fun getTransactionByMonth(year: Int, month: Int): List<TransactionModel> {
        return withContext(Dispatchers.IO) {
            val calendar = Calendar.getInstance()
            calendar.set(year, month - 1, 1)
            val date = calendar.time
            db.transactionDao().getByMonth(date = date).map { it.toModel() }
        }
    }

    suspend fun getTransactionByDay(year: Int, month: Int, day: Int): List<TransactionModel> {
        return withContext(Dispatchers.IO) {
            val calendar = Calendar.getInstance()
            calendar.set(year, month - 1, day)
            val date = calendar.time
            db.transactionDao().getByDay(date = date).map { it.toModel() }
        }
    }
}