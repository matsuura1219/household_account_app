package jp.matsuura.household_accountandroid.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onClick: (Int, String) -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    HomeScreen(
        state = state,
        onClick = onClick,
    )
}

@Composable
fun HomeScreen(
    state: HomeScreenState,
    onClick: (Int, String) -> Unit
) {
    val categories = state.categories
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        content = {
            items(count = categories.size) { index ->
                CategoryView(
                    categoryId = categories[index].id,
                    categoryName = categories[index].categoryName,
                    onClick = onClick,
                )
            }
        },
    )

}

@Composable
fun CategoryView(
    categoryId: Int,
    categoryName: String,
    onClick: (Int, String) -> Unit,
) {
    Column (
        modifier = Modifier.fillMaxWidth().clickable { onClick(categoryId, categoryName) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(
            modifier = Modifier.fillMaxWidth().height(80.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            drawCircle(
                color = Color.Blue,
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 4
            )
        }
        Text(text = categoryName, textAlign = TextAlign.Center)
    }
}