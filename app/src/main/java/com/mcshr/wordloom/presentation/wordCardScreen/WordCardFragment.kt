package com.mcshr.wordloom.presentation.wordCardScreen

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mcshr.wordloom.databinding.FragmentWordCardBinding
import com.mcshr.wordloom.domain.entities.PartOfSpeech
import com.mcshr.wordloom.presentation.utils.getColorId
import com.mcshr.wordloom.presentation.utils.getText
import com.mcshr.wordloom.presentation.utils.toResId
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordCardFragment : Fragment() {

    private var _binding: FragmentWordCardBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentWordCard binding is null")

    private val viewModel: WordCardViewModel by viewModels()

    private val translationsAdapter = TranslationListAdapter()
    private val examplesAdapter = UsageExampleInfoListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvTranslations.adapter = translationsAdapter
        binding.rvExamples.adapter = examplesAdapter

        viewModel.wordCard.observe(viewLifecycleOwner) {
            binding.tvWordText.text = it.wordText
            if (it.partOfSpeech != PartOfSpeech.EMPTY) {
                binding.tvPartOfSpeech.text = getString(it.partOfSpeech.toResId())
                binding.tvPartOfSpeech.visibility = View.VISIBLE
            } else {
                binding.tvPartOfSpeech.visibility = View.GONE
            }
            binding.tvWordStatus.backgroundTintList =  ColorStateList.valueOf(
                binding.tvWordStatus.context.getColor(it.status.getColorId())
            )
            binding.tvWordStatus.text = it.status.getText()
            translationsAdapter.submitList(it.wordTranslations)
            examplesAdapter.submitList(it.usageExamples)
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}