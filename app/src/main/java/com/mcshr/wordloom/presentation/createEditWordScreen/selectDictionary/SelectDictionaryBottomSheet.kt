package com.mcshr.wordloom.presentation.createEditWordScreen.selectDictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.FragmentSelectItemBottomSheetBinding
import com.mcshr.wordloom.presentation.createEditWordScreen.SharedCreateWordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectDictionaryBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentSelectItemBottomSheetBinding? = null
    private val binding
        get() = _binding
            ?: throw RuntimeException("CreateWord.FragmentSelectDictionaryBottomSheet Binding is null")

    private val viewModel by viewModels<SelectDictionaryViewModel>()
    private val sharedViewModel: SharedCreateWordViewModel by viewModels({requireParentFragment()})

    private val rvAdapter = DictionarySelectableListAdapter()

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
        binding.tvTitle.text = getString(R.string.select_dictionary)


        viewModel.allDictionaries.observe(viewLifecycleOwner) {
            rvAdapter.submitList(it)
        }

        sharedViewModel.selectedDictionary.observe(viewLifecycleOwner) {
            rvAdapter.selectDictionary(it.id)
        }

        binding.rvList.adapter = rvAdapter
        rvAdapter.onSelectDictionary = { dictionary ->
            sharedViewModel.selectDictionary(dictionary)
            dismiss()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}