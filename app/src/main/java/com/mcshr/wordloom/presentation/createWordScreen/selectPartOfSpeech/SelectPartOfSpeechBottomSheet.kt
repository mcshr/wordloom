package com.mcshr.wordloom.presentation.createWordScreen.selectPartOfSpeech

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.FragmentSelectItemBottomSheetBinding
import com.mcshr.wordloom.domain.entities.PartOfSpeech
import com.mcshr.wordloom.presentation.createWordScreen.SharedCreateWordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectPartOfSpeechBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentSelectItemBottomSheetBinding? = null
    private val binding
        get() = _binding
            ?: throw RuntimeException("CreateWord.FragmentSelectPOSBottomSheet Binding is null")

    private val sharedViewModel: SharedCreateWordViewModel by viewModels({ requireParentFragment() })

    private val rvAdapter = PartOfSpeechAdapter { pos ->
        sharedViewModel.selectPartOfSpeech(pos)
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectItemBottomSheetBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnCloseSheet.setOnClickListener {
            dismiss()
        }
        binding.tvTitle.text = getString(R.string.select_part_of_speech)

        sharedViewModel.selectedPartOfSpeech.observe(viewLifecycleOwner) {
            rvAdapter.selectItem(it)
        }
        rvAdapter.submitList(PartOfSpeech.entries)

        binding.rvList.adapter = rvAdapter


        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}