package jp.matsuura.household_accountandroid.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import jp.matsuura.household_accountandroid.ui.history.HistoryScreen
import jp.matsuura.household_accountandroid.ui.home.HomeScreen
import jp.matsuura.household_accountandroid.ui.item.ItemScreen

sealed class Navigation(val route: String, val title: String, val icon: ImageVector) {
    object Home : Navigation(route = "home", title = "入力", Icons.Filled.Home)
    object History : Navigation(route = "history", title = "履歴", Icons.Filled.Home)
    object Item : Navigation(route = "item", title = "集計", Icons.Filled.Home)
}

val navigationItems = listOf(
    Navigation.Home,
    Navigation.History,
    Navigation.Item,
)

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val title = remember {
        mutableStateOf("")
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .weight(1f)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Navigation.Home.route,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(Navigation.Home.route) {
                        title.value = Navigation.Home.title
                        HomeScreen(viewModel = hiltViewModel())
                    }
                    composable(Navigation.History.route) {
                        title.value = Navigation.History.title
                        HistoryScreen()
                    }
                    composable(Navigation.Item.route) {
                        title.value = Navigation.Item.title
                        ItemScreen()
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                BottomNavigation(
                    backgroundColor = Color.DarkGray,
                ) {
                    val navBackStateEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStateEntry?.destination

                    navigationItems.forEach { navigation ->
                        BottomNavigationItem(
                            icon = {
                                Icon(navigation.icon, contentDescription = null)
                            },
                            label = {
                                Text(text = navigation.title)
                            },
                            selectedContentColor = Color.White,
                            unselectedContentColor = Color.LightGray,
                            selected = currentDestination?.hierarchy?.any {
                                it.route == navigation.route
                            } == true,
                            onClick = {
                                navController.navigate(navigation.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            })
                    }
                }
            }
        }
    }
}