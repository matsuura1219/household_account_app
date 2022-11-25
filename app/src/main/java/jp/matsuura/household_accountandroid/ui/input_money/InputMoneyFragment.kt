package jp.matsuura.household_accountandroid.ui.input_money

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import jp.matsuura.householda_ccountandroid.R
import jp.matsuura.householda_ccountandroid.databinding.FragmentInputMoneyBinding

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}