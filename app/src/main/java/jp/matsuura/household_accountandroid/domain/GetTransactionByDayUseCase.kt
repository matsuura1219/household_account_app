package jp.matsuura.household_accountandroid.domain

import jp.matsuura.household_accountandroid.data.repository.TransactionRepository
import jp.matsuura.household_accountandroid.model.TransactionModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTransactionByDayUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    suspend operator fun invoke(year: Int, month: Int, day: Int): List<TransactionModel> {
        return transactionRepository.getTransactionByDay(year = year, month = month, day = day)
    }

}