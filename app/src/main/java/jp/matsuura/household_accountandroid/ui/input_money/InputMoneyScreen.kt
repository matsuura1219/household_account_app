package jp.matsuura.household_accountandroid.ui.input_money

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import jp.matsuura.household_accountandroid.ext.toStringForApp
import jp.matsuura.household_accountandroid.model.CalculatorSymbolType
import kotlinx.coroutines.launch

@Composable
fun InputMoneyScreen(
    viewModel: InputMoneyViewModel = hiltViewModel(),
    onBackPressed: (Unit) -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    InputMoneyScreen(
        state = state,
        onNumberClick = { number ->
            viewModel.onNumberClick(input = number)
        },
        onSymbolClick = { type ->
            viewModel.onSymbolClick(type = type)
        },
        onFinishButtonClick = {
            viewModel.onFinishButtonClicked()
        },
    )

    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(key1 = Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.uiEvent.collect { event ->
                    when (event) {
                        is InputMoneyScreenEvent.Success -> {
                            Toast.makeText(context, "${event.itemName}に${event.moneyMount} 円を登録しました。", Toast.LENGTH_SHORT).show()
                            onBackPressed(Unit)
                        }
                        is InputMoneyScreenEvent.Failure -> {
                            Toast.makeText(context, "登録に失敗しました。", Toast.LENGTH_SHORT).show()
                            onBackPressed(Unit)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InputMoneyScreen(
    state: InputMoneyScreenState,
    onNumberClick: (String) -> Unit,
    onSymbolClick: (CalculatorSymbolType) -> Unit,
    onFinishButtonClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = state.currentTime.toStringForApp(),
                    modifier = Modifier.padding(bottom = 12.dp),
                )
                Text(
                    text = state.category.categoryName,
                    modifier = Modifier.padding(bottom = 24.dp),
                )
                Text(
                    text = state.totalMoney.toString(),
                    modifier = Modifier.padding(bottom = 24.dp),
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    TextButton(onClick = { onSymbolClick(CalculatorSymbolType.CLEAR) }) { Text(text = "C") }
                    TextButton(onClick = { onSymbolClick(CalculatorSymbolType.PERCENTAGE) }) {
                        Text(
                            text = "%"
                        )
                    }
                    TextButton(onClick = { onSymbolClick(CalculatorSymbolType.DEL) }) { Text(text = "DEL") }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    TextButton(onClick = { onNumberClick("7") }) { Text(text = "7") }
                    TextButton(onClick = { onNumberClick("8") }) { Text(text = "8") }
                    TextButton(onClick = { onNumberClick("9") }) { Text(text = "9") }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    TextButton(onClick = { onNumberClick("4") }) { Text(text = "4") }
                    TextButton(onClick = { onNumberClick("5") }) { Text(text = "5") }
                    TextButton(onClick = { onNumberClick("6") }) { Text(text = "6") }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    TextButton(onClick = { onNumberClick("1") }) { Text(text = "1") }
                    TextButton(onClick = { onNumberClick("2") }) { Text(text = "2") }
                    TextButton(onClick = { onNumberClick("3") }) { Text(text = "3") }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    TextButton(onClick = { onNumberClick("0") }) { Text(text = "0") }
                    TextButton(onClick = { onNumberClick("00") }) { Text(text = "00") }
                    TextButton(onClick = { onSymbolClick(CalculatorSymbolType.PERIOD) }) { Text(text = ".") }
                }
            }

            Button(onClick = onFinishButtonClick) {
                Text(text = "入力")
            }
        }

    }
}