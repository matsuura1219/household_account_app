package jp.matsuura.household_accountandroid.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {

    val list = listOf<String>(
        "食費",
        "外食",
        "レジャー",
        "日用品",
        "交際",
        "交通",
        "通信費",
        "税金",
        "住まい",
    )
    
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        content = {
                  items(count = list.size) { index ->
                      Text(text = list[index])
                  }
        },
    )
}