package com.mcshr.wordloom.presentation.createDictionaryScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.FragmentEditDictionaryBinding
import com.mcshr.wordloom.domain.entities.Language
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateDictionaryFragment : Fragment() {

    private var _binding: FragmentEditDictionaryBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentEditDictionaryBinding is null")

    private val viewModel: CreateDictionaryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditDictionaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.saveAndClose.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
            } else {
                showErrorSnackBar(getString(R.string.error_already_exists_dictionary))
            }
        }
        viewModel.allLanguages.observe(viewLifecycleOwner) { languages ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                languages.map { it.name })
            binding.autocompleteDictWordLanguage.setAdapter(adapter)
            binding.autocompleteDictMeaningLanguage.setAdapter(adapter)
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.toolbar.setOnMenuItemClickListener { option ->
            when (option.itemId) {
                R.id.menu_item_save -> {
                    saveDictionary()
                    true
                }

                else -> false
            }
        }
    }

    private fun saveDictionary() {
        val dictName = binding.editTextDictName.text.toString()
        if (dictName.isEmpty()) {
            binding.editTextDictName.error = getString(R.string.error_empty_field)
            return
        }
        val languageWord = getLanguageByName(
            binding.autocompleteDictWordLanguage.text.toString()
        )
        val languageMeaning = getLanguageByName(
            binding.autocompleteDictMeaningLanguage.text.toString()
        )
        if (languageWord == null || languageMeaning == null) {
            showErrorSnackBar(getString(R.string.error_empty_languages))
            return
        }
        if (languageMeaning == languageWord) {
            showErrorSnackBar(getString(R.string.error_same_languages))
            return
        }
        viewModel.createDictionary(dictName, languageWord, languageMeaning)
    }

    private fun getLanguageByName(name: String): Language? {
        return viewModel.allLanguages.value?.find { it.name == name }
    }

    private fun showErrorSnackBar(errorText: String) {
        Snackbar.make(
            binding.root, errorText, Snackbar.LENGTH_SHORT
        ).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}