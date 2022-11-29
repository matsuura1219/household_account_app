package jp.matsuura.household_accountandroid.ui.common

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.matsuura.household_accountandroid.model.CalculatorType
import jp.matsuura.householda_ccountandroid.R

class CalculatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
) : ConstraintLayout(context, attrs) {

    var onValueClicked: ((Int) -> Unit)? = null

    private val adapter: Adapter
    private val recyclerView: RecyclerView
    private val textView: TextView

    private val defaultItemList: List<CalculatorType> = listOf(
        CalculatorType.Number(7),
        CalculatorType.Number(8),
        CalculatorType.Number(9),
        CalculatorType.Signal("今日"),
        CalculatorType.Number(4),
        CalculatorType.Number(5),
        CalculatorType.Number(6),
        CalculatorType.Signal("+"),
        CalculatorType.Number(1),
        CalculatorType.Number(2),
        CalculatorType.Number(3),
        CalculatorType.Signal("-"),
        CalculatorType.Signal("."),
        CalculatorType.Number(0),
        CalculatorType.Signal("DEL"),
        CalculatorType.Signal("OK"),
    )

    private var currentData: Int = 0

    companion object {
        const val MAX_LENGTH: Int = 9
    }

    init {
        inflate(context, R.layout.item_calculator_list, this)
        adapter = Adapter(
            onClick = { calculatorType ->
                handleInputData(input = calculatorType)
                onValueClicked?.invoke(currentData)
            }
        )
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 4, RecyclerView.VERTICAL, false)
        adapter.update(items = defaultItemList)
        textView = findViewById(R.id.inputData)
        textView.text = currentData.toString()
    }

    private fun handleInputData(input: CalculatorType) {
        when (input) {
            is CalculatorType.Signal -> {
                handleSignal(signal = input.signal)
            }
            is CalculatorType.Number -> {
                handleNumber(number = input.number)
            }
        }
    }

    private fun handleNumber(number: Int) {
        if (currentData.toString().length >= MAX_LENGTH) return
        currentData = if (currentData == 0) {
            number
        } else {
            val str = currentData.toString() + number.toString()
            str.toInt()
        }
        textView.text = currentData.toString()
    }

    private fun handleSignal(signal: String) {
        when (signal) {
            "DEL" -> {
                currentData /= 10
                textView.text = currentData.toString()
            }
        }
    }

    class Adapter(
        private val onClick: (CalculatorType) -> Unit,
    ) : RecyclerView.Adapter<Adapter.CalculatorViewHolder>() {

        private var dataSet: List<CalculatorType> = emptyList()

        @SuppressLint("NotifyDataSetChanged")
        fun update(items: List<CalculatorType>) {
            if (dataSet == items) return
            dataSet = items
            notifyDataSetChanged()
        }

        class CalculatorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val buttonText: TextView
            init {
                buttonText = view.findViewById(R.id.buttonText)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calculator, parent, false)
            return CalculatorViewHolder(view)
        }

        override fun onBindViewHolder(holder: CalculatorViewHolder, position: Int) {
            val text = when (val value = dataSet[position]) {
                is CalculatorType.Signal -> value.signal
                is CalculatorType.Number -> value.number.toString()
            }
            holder.buttonText.text = text
            holder.buttonText.setOnClickListener {
                onClick(dataSet[position])
            }
        }

        override fun getItemCount(): Int {
            return dataSet.size
        }
    }
}