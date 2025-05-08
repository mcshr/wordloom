package com.mcshr.wordloom.presentation.homeScreen.selectDictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mcshr.wordloom.databinding.FragmentSelectDictionaryBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectDictionaryBottomSheet:BottomSheetDialogFragment() {
    private var _binding: FragmentSelectDictionaryBottomSheetBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("Home.FragmentSelectDictionaryBottomSheetBinding is null")
    private val viewModel by viewModels<SelectDictionaryViewModel>()
    private val dictAdapter = SelectableDictListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectDictionaryBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnCloseSheet.setOnClickListener {
            dismiss()
        }

        viewModel.allDictionaries.observe(viewLifecycleOwner){
            dictAdapter.submitList(it)
        }
        dictAdapter.onSelectDictionary = {
            dictionary ->
            viewModel.selectDictionary(dictionary)
        }
        binding.rvDictionarySelectList.adapter = dictAdapter


        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}