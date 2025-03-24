package com.mcshr.wordloom.presentation.editWordScreen.selectDictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mcshr.wordloom.databinding.FragmentSelectDictionaryBottomSheetBinding
import com.mcshr.wordloom.presentation.editWordScreen.SharedDictionarySelectViewModel

class SelectDictionaryBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentSelectDictionaryBottomSheetBinding? = null
    private val binding
        get() = _binding
            ?: throw RuntimeException(" FragmentSelectDictionaryBottomSheetBinding is null")

    private val viewModel by lazy{
        ViewModelProvider(this)[SelectDictionaryViewModel::class.java]
    }
    private val sharedViewModel: SharedDictionarySelectViewModel by activityViewModels()
    private val rvAdapter = DictionarySelectableListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectDictionaryBottomSheetBinding.inflate(
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

        viewModel.allDictionaries.observe(viewLifecycleOwner) {
            rvAdapter.submitList(it)
        }

        sharedViewModel.selectedDictionary.observe(viewLifecycleOwner) {
            rvAdapter.selectDictionary(it.id)
        }

        binding.rvDictionarySelectList.adapter = rvAdapter
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