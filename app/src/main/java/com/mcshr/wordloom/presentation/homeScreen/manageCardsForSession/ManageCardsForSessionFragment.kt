package com.mcshr.wordloom.presentation.homeScreen.manageCardsForSession

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mcshr.wordloom.databinding.FragmentManageCardsForSessionBinding

class ManageCardsForSessionFragment : Fragment() {

    private var _binding: FragmentManageCardsForSessionBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException(" FragmentManageCardsForSessionBinding is null")

    private val args: ManageCardsForSessionFragmentArgs by navArgs()
    private val viewModel: ManageCardsForSessionViewModel by viewModels{
        ManageCardsForSessionViewModel.provideFactory(args.dictinaryId, requireActivity().application)
    }
    private val wordsAdapter = FilteredWordListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageCardsForSessionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.title = viewModel.dictionaryId.toString()

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.dictionaryLiveData.observe(viewLifecycleOwner){
            binding.toolbar.title = it.name
        }

        viewModel.wordCardList.observe(viewLifecycleOwner){
            wordsAdapter.submitList(it)
        }

        wordsAdapter.onWordClick = {
            wordCard ->
            viewModel.toggleReadyToLearnState(wordCard)
        }
        binding.rvWordCardsList.adapter  = wordsAdapter

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().popBackStack()
        }
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}