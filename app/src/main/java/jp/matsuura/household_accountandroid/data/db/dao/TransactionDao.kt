package jp.matsuura.household_accountandroid.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import jp.matsuura.household_accountandroid.data.db.entity.TransactionEntity
import java.time.LocalDate

@Dao
interface TransactionDao {

    @Insert
    fun insert(entity: TransactionEntity)
}