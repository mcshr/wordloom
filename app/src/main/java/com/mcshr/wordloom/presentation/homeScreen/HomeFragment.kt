package com.mcshr.wordloom.presentation.homeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mcshr.wordloom.databinding.FragmentHomeBinding
import com.mcshr.wordloom.presentation.homeScreen.selectDictionary.SelectDictionaryBottomSheet
import com.mcshr.wordloom.presentation.homeScreen.sessionSettings.SessionSettingsFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

    private val viewModel by viewModels<HomeViewModel>()
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
            var readyToLearn = 0
            var total = 0
            var learned = 0
            var unknown = 0
            var learning = 0
            var repeat = 0 //TODO
            list.forEach {
                readyToLearn += it.readyToLearnCountCards
                total += it.totalCountCards
                learned += it.learnedCountCards
                unknown += it.unknownCountCards
                learning +=it.learningCountCards
            }
            binding.tvCard1ReadyToLearn.text = readyToLearn.toString()
            binding.tvCard2Learning.text = learning.toString()
            binding.tvCard2Repeat.text = (666).toString()
            binding.tvCard3TotalWords.text = total.toString()
            binding.tvCard4Learned.text = learned.toString()
            binding.tvCard5Unknown.text = unknown.toString()

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