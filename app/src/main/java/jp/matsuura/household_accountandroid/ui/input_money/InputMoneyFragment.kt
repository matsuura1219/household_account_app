package jp.matsuura.household_accountandroid.ui.input_money

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import jp.matsuura.household_accountandroid.ext.showSnackBar
import jp.matsuura.household_accountandroid.ext.toStringDateForApp
import jp.matsuura.household_accountandroid.ext.toStringTimeForApp
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

    private val args: InputMoneyFragmentArgs by navArgs()

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
        initView()
        handleListener()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                handleUiState(coroutineScope = this)
                handleUiEvent(coroutineScope = this)
            }
        }
    }

    private fun initView() {
        binding.categoryMenuTextView.setText(args.categoryName)
    }

    private fun handleListener() {
        binding.calculatorView.onValueClicked = {
            viewModel.onCalculatorClicked(value = it)
        }
        binding.confirmButton.setOnClickListener {
            viewModel.onConfirmButtonClicked()
        }
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.categoryMenuTextView.setOnItemClickListener { adapterView, view, i, l ->
            val itemName = adapterView.getItemAtPosition(i) as String
            viewModel.onItemSelected(itemName = itemName)
        }
        binding.dateTextView.setOnClickListener {
            MaterialDatePicker.Builder.datePicker().build().apply {
                addOnPositiveButtonClickListener { time: Long ->
                    viewModel.onDateChanged(time = time)
                }
            }.show(parentFragmentManager, "Tag")
        }
    }

    private fun handleUiState(coroutineScope: CoroutineScope) {
        viewModel.uiState.onEach {
            val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_menu_popup, it.categoryItemList.map { category -> category.categoryName })
            binding.categoryMenuTextView.setAdapter(adapter)
            binding.totalMoneyTextView.text = it.totalMoney.toString()
            binding.dateTextView.text = it.currentDate.toStringDateForApp()
        }.launchIn(coroutineScope)
    }

    private fun handleUiEvent(coroutineScope: CoroutineScope) {
        viewModel.uiEvent.onEach {
            when (it) {
                is InputMoneyViewModel.UiEvent.Success -> {
                    findNavController().popBackStack()
                    requireView().showSnackBar(message = "${it.categoryName}に${it.totalMoney}円\n登録が完了しました。")
                }
                is InputMoneyViewModel.UiEvent.Failure -> {
                    findNavController().popBackStack()
                    requireView().showSnackBar(message = "登録に失敗しました。")
                }
                is InputMoneyViewModel.UiEvent.NotInputMoney -> {
                    requireView().showSnackBar(message = "金額を入力してください。")
                }
            }
        }.launchIn(coroutineScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}