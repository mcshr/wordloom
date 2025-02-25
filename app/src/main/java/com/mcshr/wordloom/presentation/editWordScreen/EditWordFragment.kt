package com.mcshr.wordloom.presentation.editWordScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.FragmentEditWordBinding
import com.mcshr.wordloom.presentation.editWordScreen.selectDictionary.SelectDictionaryBottomSheet

class EditWordFragment : Fragment() {

    private var _binding: FragmentEditWordBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentEditWordBinding is null")

    private val viewModel: EditWordViewModel by viewModels()
    private val sharedViewModel: SharedDictionarySelectViewModel by activityViewModels()

    private val meaningAdapter = MeaningListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedViewModel.selectedDictionary.observe(viewLifecycleOwner) {
            binding.toolbar.title = it.name
        }

        binding.rvMeaningList.adapter = meaningAdapter
        meaningAdapter.deleteMeaning = { meaning -> viewModel.deleteMeaning(meaning) }
        meaningAdapter.updateMeaning = { meaning ->
            binding.editTextMeaning.setText(viewModel.deleteMeaning(meaning))
        }
        viewModel.meaningList.observe(viewLifecycleOwner) {
            meaningAdapter.submitList(it)
        }

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.toolbar.setOnClickListener {
            SelectDictionaryBottomSheet().show(parentFragmentManager, "SelectDictionaryTag")
        }
        binding.toolbar.setOnMenuItemClickListener { option ->
            when (option.itemId) {
                R.id.menu_item_save -> {
                    saveWord()
                    true
                }

                else -> throw RuntimeException("Unknown option ID: ${option.itemId}")
            }
        }

        binding.btnAddMeaning.setOnClickListener {
            val meaning = binding.editTextMeaning.text.toString()
            if (meaning.isEmpty()) {
                binding.editTextMeaning.error = getString(R.string.error_empty_field)
            } else if (!viewModel.addMeaning(meaning))
                binding.editTextMeaning.error = getString(R.string.error_already_exists_meaning)
            else {
                binding.editTextMeaning.editableText.clear()
            }
        }

        viewModel.saveAndClose.observe(viewLifecycleOwner){
            if(it){
                findNavController().popBackStack()
            }else{
                Snackbar.make(
                    binding.root,
                    getString(R.string.error_already_exists_word),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun saveWord() {
        val wordText = binding.editTextWord.text.toString()
        val meaningsList = viewModel.meaningList.value.orEmpty()
        if (wordText.isEmpty()) {
            binding.editTextWord.error = getString(R.string.error_empty_field)
            return
        }
        if (meaningsList.isEmpty()) {
            Snackbar.make(
                binding.root,
                getString(R.string.error_no_meanings),
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }
        viewModel.createWordCard(wordText, meaningsList)
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}