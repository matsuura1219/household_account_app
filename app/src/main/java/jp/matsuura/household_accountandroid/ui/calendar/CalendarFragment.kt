package jp.matsuura.household_accountandroid.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import jp.matsuura.householda_ccountandroid.R
import jp.matsuura.householda_ccountandroid.databinding.FragmentCalendarBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CalendarViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleListener()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                handleUiState(coroutineScope = this)
            }
        }
    }

    private fun handleListener() {
        binding.calendarView.onDayClicked = { year, month, day ->
            // TODO: 日付押下時の処理を実装します
        }
        binding.backButton.setOnClickListener {
           viewModel.onBackButtonClicked()
        }
        binding.nextButton.setOnClickListener {
            viewModel.onNextButtonClicked()
        }
    }

    private fun handleUiState(coroutineScope: CoroutineScope) {
        viewModel.uiState.onEach {
            binding.monthTextView.text = it.currentYear.toString() + "年" + it.currentMonth.toString() + "月"
            binding.calendarView.update(year = it.currentYear, month =  it.currentMonth, transactionList = it.transactionList)
        }.launchIn(coroutineScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}