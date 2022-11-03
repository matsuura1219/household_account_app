package jp.matsuura.household_accountandroid.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "category_name") val categoryName: String,
    @ColumnInfo(name = "icon_color") val iconColor: String,
)
