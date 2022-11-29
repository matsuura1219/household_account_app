package jp.matsuura.household_accountandroid.ui.input_money.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import jp.matsuura.household_accountandroid.ui.input_money.recyclerview.RecyclerViewAdapter
import jp.matsuura.householda_ccountandroid.R
import jp.matsuura.householda_ccountandroid.databinding.FragmentCategoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.fragment_category) {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CategoryViewModel>()

    private lateinit var adapter: RecyclerViewAdapter

    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        args?.let {
            position = it.getInt(POSITION, -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        handleListener()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                handelUiState(coroutineScope = this)
                handleUiEvent(coroutineScope = this)
            }
        }
    }

    private fun initView() {
        binding.recyclerView.setHasFixedSize(true)
        adapter = RecyclerViewAdapter(
            onItemClicked = {
                viewModel.onItemClicked(category = it)
            }
        )
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 4, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

    private fun handleListener() {
        binding.calculatorView.onValueClicked =  { number ->
            viewModel.onInputMoney(amount = number)
        }
    }

    private fun handelUiState(coroutineScope: CoroutineScope) {
        viewModel.uiState.onEach {
            if (position == 0) {
                adapter.update(items = it.spendingCategoryList)
            } else {
                adapter.update(items = it.incomeCategoryList)
            }
            binding.calculatorView.isGone = !it.showCalculator
        }.launchIn(coroutineScope)
    }

    private fun handleUiEvent(coroutineScope: CoroutineScope) {
        viewModel.uiEvent.onEach {
            when (it) {
                is CategoryViewModel.UiEvent.Failure -> {}
            }
        }.launchIn(coroutineScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val POSITION: String = "POSITION"
        fun newInstance(position: Int): CategoryFragment {
            val fragment = CategoryFragment()
            val bundle = Bundle()
            bundle.putInt(POSITION, position)
            fragment.arguments = bundle
            return fragment
        }
    }

}