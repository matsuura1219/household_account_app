package jp.matsuura.household_accountandroid.ui.input_money

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun InputMoneyScreen(
    viewModel: InputMoneyViewModel = hiltViewModel()
) {
    Text(text = "InputMoneyScreenです")
}