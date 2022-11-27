package jp.matsuura.household_accountandroid.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.matsuura.household_accountandroid.data.db.AppDatabase
import jp.matsuura.household_accountandroid.util.Const
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatasourceModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "household_app_db")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    executeSql(db = db, type = 0)
                    executeSql(db = db, type = 1)
                }
            }
            ).build()
    }

    private fun executeSql(db: SupportSQLiteDatabase,type: Int) {
        if (type != 0 && type != 1) {
            return
        }
        val sqlBuilder = StringBuilder()
        val defaultCategory = if (type == 0)  {
            Const.DEFAULT_SPENDING_CATEGORIES
        } else {
            Const.DEFAULT_INCOME_CATEGORIES
        }
        val constValue = "INSERT INTO 'category' VALUES"
        sqlBuilder.append(constValue)
        defaultCategory.forEachIndexed { index, string ->
            val idx = if (type == 0) index else index + 100
            if (index == defaultCategory.size - 1) {
                sqlBuilder.append("($idx, '${string}', ${type})")
            } else {
                sqlBuilder.append("($idx, '${string}', ${type}),")
            }
        }
        db.execSQL(sqlBuilder.toString())
    }

}