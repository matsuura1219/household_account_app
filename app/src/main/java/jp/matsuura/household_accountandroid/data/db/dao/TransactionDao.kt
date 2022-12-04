package jp.matsuura.household_accountandroid.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import jp.matsuura.household_accountandroid.data.db.entity.TransactionEntity
import java.util.Date

@Dao
interface TransactionDao {

    @Insert
    fun insert(entity: TransactionEntity)

    @Query("SELECT * FROM money_transaction WHERE datetime(created_at/1000,'unixepoch','start of month') = datetime(:dateOfMonth/1000,'unixepoch','start of month')")
    fun getByMonth(dateOfMonth: Date): List<TransactionEntity>

}