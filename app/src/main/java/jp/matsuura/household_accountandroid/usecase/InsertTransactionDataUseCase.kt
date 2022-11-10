package jp.matsuura.household_accountandroid.usecase

import jp.matsuura.household_accountandroid.data.repository.TransactionRepository
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertTransactionDataUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {

    suspend operator fun invoke(categoryId: Int, moneyMount: Int, currentTime: Date) {
        transactionRepository.insertTransaction(
            categoryId = categoryId,
            moneyMount = moneyMount,
            currentTime = currentTime,
        )
    }
}