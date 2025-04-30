package com.mcshr.wordloom.presentation.homeScreen.selectedDictionaries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mcshr.wordloom.databinding.FragmentSelectedDictionariesBinding
import com.mcshr.wordloom.presentation.homeScreen.HomeViewModel

class SelectedDictionariesFragment : Fragment() {
    private var _binding: FragmentSelectedDictionariesBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentSelectedDictionariesBinding is null")

    private val viewModel by activityViewModels<HomeViewModel>()
    private val selectedDictsAdapter = SelectedDictionariesListAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectedDictionariesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.selectedDictionaries.observe(viewLifecycleOwner) {
            selectedDictsAdapter.submitList(it)
        }
        selectedDictsAdapter.onDictionaryClick = { dictId ->
            val action =
                SelectedDictionariesFragmentDirections.actionSelectedDictionariesFragmentToManageCardsForSessionFragment(
                    dictId
                )
            findNavController().navigate(action)
        }
        binding.rvSelectedDictionariesList.adapter = selectedDictsAdapter

        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}