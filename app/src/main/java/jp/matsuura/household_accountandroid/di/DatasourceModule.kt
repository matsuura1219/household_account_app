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
                    val sqlBuilder = StringBuilder()
                    val defaultCategories = Const.DEFAULT_CATEGORIES
                    val constValue = "INSERT INTO 'category' VALUES"
                    sqlBuilder.append(constValue)
                    defaultCategories.forEachIndexed { index, string ->
                        if (index == defaultCategories.size - 1) {
                            sqlBuilder.append("($index, '$string')")
                        } else {
                            sqlBuilder.append("($index, '$string'),")
                        }
                    }
                    db.execSQL(sqlBuilder.toString())
                }
            }
            ).build()
    }

}