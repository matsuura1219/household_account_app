package jp.matsuura.household_accountandroid.ui.input_money

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
import jp.matsuura.household_accountandroid.ext.toStringForApp
import jp.matsuura.householda_ccountandroid.R
import jp.matsuura.householda_ccountandroid.databinding.FragmentInputMoneyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InputMoneyFragment : Fragment(R.layout.fragment_input_money) {

    private var _binding: FragmentInputMoneyBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<InputMoneyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputMoneyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                handleUiState(coroutineScope = this)
                handleUiEvent(coroutineScope = this)
            }
        }
    }

    private fun handleUiState(coroutineScope: CoroutineScope) {
        viewModel.uiState.onEach {
            binding.categoryTextView.text = it.categoryName
            binding.totalMoneyTextView.text = it.totalMoney.toString()
            binding.dateTextView.text = it.currentDate.toStringForApp()
        }.launchIn(coroutineScope)
    }

    private fun handleUiEvent(coroutineScope: CoroutineScope) {
        viewModel.uiEvent.onEach {
            when (it) {
                is InputMoneyViewModel.UiEvent.Success -> {}
                is InputMoneyViewModel.UiEvent.Failure -> {}
            }
        }.launchIn(coroutineScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}