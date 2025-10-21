package com.mcshr.wordloom.presentation.dictionaryScreen

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.FragmentWordMenuDialogBinding
import com.mcshr.wordloom.domain.entities.WordCard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordMenuDialogFragment : DialogFragment() {
    private val viewModel: DictionaryViewModel by viewModels(
        { requireParentFragment() }
    )

    private var _binding: FragmentWordMenuDialogBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException(
            "FragmentWordMenuMenuDialogBinding is null"
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
        _binding = FragmentWordMenuDialogBinding.inflate(
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

        binding.btnEdit.setOnClickListener {
            wordCard?.let {
                val action = DictionaryFragmentDirections.actionDictionaryFragmentToEditWordFragment(
                    it.id
                )
                findNavController().navigate(action)
            }
            dismiss()
        }

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
        fun newInstance(wordCard: WordCard): WordMenuDialogFragment{
            return WordMenuDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(WORD_CARD_KEY, wordCard)
                }
            }
        }

        private const val WORD_CARD_KEY = "wckey"
    }
}