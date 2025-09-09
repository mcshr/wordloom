package com.mcshr.wordloom.presentation.dictionaryMenuDialog

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.mcshr.wordloom.R

import com.mcshr.wordloom.databinding.FragmentWordCardMenuDialogBinding
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.presentation.dictionaryScreen.DictionaryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        val wordCard: WordCard? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(WORD_CARD_KEY, WordCard::class.java)
        } else {
            @Suppress("DEPRECATION")
            requireArguments().getParcelable(WORD_CARD_KEY)
        }


        binding.tvWord.text = wordCard?.wordText

        binding.btnDelete.setOnClickListener {
            wordCard?.let {
                viewModel.deleteWordCard(wordCard)
            }
            dismiss()
        }
        binding.btnClose.setOnClickListener {
            dismiss()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(wordCard: WordCard): WordCardMenuDialogFragment{
            return WordCardMenuDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(WORD_CARD_KEY, wordCard)
                }
            }
        }

        private const val WORD_CARD_KEY = "wckey"
    }
}