package com.mcshr.wordloom.presentation.libraryScreen

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.FragmentDictionaryMenuDialogBinding
import com.mcshr.wordloom.domain.entities.Dictionary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DictionaryMenuDialogFragment : DialogFragment() {
    private val libraryViewModel by viewModels<LibraryViewModel>(
        { requireParentFragment() }
    )
    private var _binding: FragmentDictionaryMenuDialogBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException(
            " FragmentDictionaryMenuDialogBinding is null"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.WordloomDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDictionaryMenuDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dictionary = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(DICTIONARY_KEY, Dictionary::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(DICTIONARY_KEY)
        }
        dictionary?.let{
            binding.tvDictionaryName.text = dictionary.name

            binding.btnDelete.setOnClickListener {
                libraryViewModel.deleteDictionary(dictionary)
                dismiss()
            }
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }

    }

    companion object {
        fun newInstance(dictionary: Dictionary): DictionaryMenuDialogFragment {
            return DictionaryMenuDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DICTIONARY_KEY, dictionary)
                }
            }
        }

        const val DICTIONARY_KEY = "dict"
    }
}