package com.mcshr.wordloom.presentation.dictionaryMenuDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.mcshr.wordloom.R

import com.mcshr.wordloom.databinding.FragmentWordCardMenuDialogBinding
import com.mcshr.wordloom.presentation.dictionaryScreen.DictionaryViewModel

class WordCardMenuDialogFragment : DialogFragment() {
    private val viewModel: DictionaryViewModel by viewModels(
        { requireParentFragment() }
    )

    private var _binding: FragmentWordCardMenuDialogBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException(
            "FragmentWordCardMenuMenuDialogBinding is null"
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
        _binding = FragmentWordCardMenuDialogBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}