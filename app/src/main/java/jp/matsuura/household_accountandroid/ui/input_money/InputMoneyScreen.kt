package jp.matsuura.household_accountandroid.ui.input_money

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import jp.matsuura.household_accountandroid.ui.Navigation
import jp.matsuura.household_accountandroid.ui.history.HistoryScreen
import jp.matsuura.household_accountandroid.ui.home.HomeScreen
import jp.matsuura.household_accountandroid.ui.item.ItemScreen
import jp.matsuura.household_accountandroid.ui.navigationItems

@Composable
fun InputMoneyScreen(
    viewModel: InputMoneyViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    InputMoneyScreen(
        state = state,
    )
}

@Composable
fun InputMoneyScreen(
    state: InputMoneyScreenState,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = state.category.id.toString())
            Text(text = state.category.categoryName)
        }
    }
}