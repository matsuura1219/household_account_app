package jp.matsuura.household_accountandroid.domain

import jp.matsuura.household_accountandroid.data.repository.TransactionRepository
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {

    suspend operator fun invoke(categoryId: Int, money: Int, currentTime: Date) {
        transactionRepository.insertTransaction(
            categoryId = categoryId,
            money = money,
            currentTime = currentTime,
        )
    }
}