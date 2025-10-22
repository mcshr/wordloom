package com.mcshr.wordloom.presentation.createEditWordScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.FragmentEditWordBinding
import com.mcshr.wordloom.presentation.createEditWordScreen.selectDictionary.SelectDictionaryBottomSheet
import com.mcshr.wordloom.presentation.createEditWordScreen.selectPartOfSpeech.SelectPartOfSpeechBottomSheet
import com.mcshr.wordloom.presentation.utils.toResId
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateEditWordFragment : Fragment() {

    private var _binding: FragmentEditWordBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentEditWordBinding is null")

    private val viewModel: CreateEditWordViewModel by viewModels()
    private val sharedViewModel: SharedCreateWordViewModel by viewModels()

    private val translationAdapter = TranslationListAdapter()

    private val usageExampleAdapter = UsageExampleListAdapter(
        { example, text ->
            viewModel.updateExample(example, text)
        }, { example, text ->
            viewModel.updateTranslation(example, text)
        },
        { example ->
            viewModel.deleteExample(example)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        setupToolbar()
        setupButtons()
        observeViewModel()
        observeSharedViewModel()
    }

    private fun observeSharedViewModel() {
        sharedViewModel.selectedDictionary.observe(viewLifecycleOwner) {
            if (viewModel.isCreationMode) binding.toolbar.title = it.name
            binding.textInputLayout4.hint = it.languageOriginal.name
            binding.textInputLayout6.hint = it.languageTranslation.name
        }
        sharedViewModel.selectedPartOfSpeech.observe(viewLifecycleOwner) {
            binding.btnPartOfSpeech.text = getString(it.toResId())
        }
    }

    private fun setupButtons() {
        binding.btnAddMeaning.setOnClickListener {
            val translation = binding.editTextMeaning.text.toString()
            if (translation.isEmpty()) {
                binding.editTextMeaning.error = getString(R.string.error_empty_field)
            } else if (!viewModel.addTranslation(translation))
                binding.editTextMeaning.error = getString(R.string.error_already_exists_meaning)
            else {
                binding.editTextMeaning.editableText.clear()
            }
        }

        binding.btnPartOfSpeech.setOnClickListener {
            SelectPartOfSpeechBottomSheet().show(
                childFragmentManager,
                "SelectPartOfSpeechDialogTag"
            )
        }

        binding.btnAddUsageExample.setOnClickListener {
            viewModel.addExample()
        }
    }

    private fun observeViewModel() {
        viewModel.translationList.observe(viewLifecycleOwner) {
            translationAdapter.submitList(it)
        }

        viewModel.examplesList.observe(viewLifecycleOwner) {
            usageExampleAdapter.submitList(it)
        }

        viewModel.oldWordCard.observe(viewLifecycleOwner) {
            binding.editTextWord.setText(it.wordText)
            sharedViewModel.selectPartOfSpeech(it.partOfSpeech)
        }

        viewModel.saveAndClose.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.error_already_exists_word),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupAdapters() {
        binding.rvTranslationList.adapter = translationAdapter
        translationAdapter.deleteTranslation = { meaning -> viewModel.deleteTranslation(meaning) }
        translationAdapter.updateTranslation = { meaning ->
            binding.editTextMeaning.setText(viewModel.deleteTranslation(meaning))
        }

        binding.rvUsageExampleList.adapter = usageExampleAdapter
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        if (viewModel.isCreationMode) {
            binding.toolbar.setOnClickListener {
                SelectDictionaryBottomSheet().show(childFragmentManager, "SelectDictionaryTag")
            }
        } else {
            binding.toolbar.subtitle = null
        }

        binding.toolbar.setOnMenuItemClickListener { option ->
            when (option.itemId) {
                R.id.menu_item_save -> {
                    saveWordToDictionary()
                    true
                }

                else -> throw RuntimeException("Unknown option ID: ${option.itemId}")
            }
        }
    }

    private fun saveWordToDictionary() {
        val wordText = binding.editTextWord.text.toString()
        val translationList = viewModel.translationList.value.orEmpty()
        val dictionary = sharedViewModel.selectedDictionary.value
        val pos = sharedViewModel.selectedPartOfSpeech.value ?: return
        if (wordText.isEmpty()) {
            binding.editTextWord.error = getString(R.string.error_empty_field)
            return
        }
        if (translationList.isEmpty()) {
            Snackbar.make(
                binding.root,
                getString(R.string.error_no_meanings),
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }
        if (dictionary == null) {
            Snackbar.make(
                binding.root,
                "ERROR: No dictionary",
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }
        viewModel.saveWordCard(
            wordText,
            translationList,
            dictionary,
            pos
        )
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}