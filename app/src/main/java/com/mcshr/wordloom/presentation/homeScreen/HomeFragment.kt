package com.mcshr.wordloom.presentation.homeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.FragmentHomeBinding
import com.mcshr.wordloom.presentation.homeScreen.selectDictionary.SelectDictionaryBottomSheet
import com.mcshr.wordloom.presentation.homeScreen.selectedDictionaries.SelectedDictionariesListAdapter
import com.mcshr.wordloom.presentation.homeScreen.sessionSettings.SessionSettingsFragment
import com.mcshr.wordloom.presentation.utils.setDebounceOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

    private val viewModel by activityViewModels<HomeViewModel>()
    private val selectedDictsAdapter = SelectedDictionariesListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.btnMainLearn.setDebounceOnClickListener {
            SessionSettingsFragment().show(parentFragmentManager, "SessionSettingsTag")
        }
        binding.btnAddMoreDicts.setDebounceOnClickListener {
            lifecycleScope.launch {
                if (viewModel.checkIfAnyDictionaryExist()) {
                    SelectDictionaryBottomSheet().show(
                        parentFragmentManager,
                        "AddSelectableDictionaryTag"
                    )
                } else {
                    Toast.makeText(
                        context,
                        context?.getString(R.string.warning_no_dictionary),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.selectedDictionaries.observe(viewLifecycleOwner) { list ->
            selectedDictsAdapter.submitList(list)
        }
        viewModel.stats.observe(viewLifecycleOwner) {
            binding.tvCard1ReadyToLearn.text = it.readyToLearn.toString()
            binding.tvCard2Learning.text = it.learning.toString()
            binding.tvCard3TotalWords.text = it.total.toString()
            binding.tvCard4Learned.text = it.learned.toString()
            binding.tvCard5Unknown.text = it.unknown.toString()
        }
        viewModel.repeatCount.observe(viewLifecycleOwner) {
            binding.tvCard2Repeat.text = it.toString()
        }
        selectedDictsAdapter.onDictionaryClick = { dictId ->
            val action =
                HomeFragmentDirections.actionHomeFragmentToManageCardsForSessionFragment(dictId)
            findNavController().navigate(action)
        }
        binding.rvSelectedDictionariesList.adapter = selectedDictsAdapter
        binding.tvCard1ReadyToLearn.text

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}