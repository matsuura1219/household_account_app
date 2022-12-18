package jp.matsuura.household_accountandroid.domain

import jp.matsuura.household_accountandroid.data.repository.TransactionRepository
import jp.matsuura.household_accountandroid.model.TransactionModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTransactionByMonthUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    suspend operator fun invoke(year: Int, month: Int): List<TransactionModel> {
        return transactionRepository.getTransactionByMonth(year = year, month = month)
    }

}