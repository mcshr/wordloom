package com.mcshr.wordloom.presentation.homeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mcshr.wordloom.databinding.FragmentHomeBinding
import com.mcshr.wordloom.presentation.homeScreen.selectDictionary.SelectDictionaryBottomSheet
import com.mcshr.wordloom.presentation.homeScreen.selectedDictionaries.SelectedDictionariesListAdapter
import com.mcshr.wordloom.presentation.homeScreen.sessionSettings.SessionSettingsFragment

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

        binding.btnMainLearn.setOnClickListener {
            SessionSettingsFragment().show(parentFragmentManager,"SessionSettingsTag")
        }

        binding.btnAddMoreDicts.setOnClickListener {
            SelectDictionaryBottomSheet().show(parentFragmentManager, "AddSelectableDictionaryTag")
        }

        viewModel.selectedDictionaries.observe(viewLifecycleOwner){list->
            selectedDictsAdapter.submitList(list)
        }
        viewModel.stats.observe(viewLifecycleOwner){
            binding.tvCard1ReadyToLearn.text = it.readyToLearn.toString()
            binding.tvCard2Learning.text = it.learning.toString()
            binding.tvCard3TotalWords.text = it.total.toString()
            binding.tvCard4Learned.text = it.learned.toString()
            binding.tvCard5Unknown.text = it.unknown.toString()
        }
        viewModel.repeatCount.observe(viewLifecycleOwner){
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