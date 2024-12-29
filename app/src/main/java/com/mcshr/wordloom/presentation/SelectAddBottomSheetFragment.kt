package com.mcshr.wordloom.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.FragmentSelectAddBottomSheetBinding

class SelectAddBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSelectAddBottomSheetBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentSelectAddBottomSheetBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectAddBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnCloseSheetAdd.setOnClickListener {
            dismiss()
        }
        binding.btnWordAdd.setOnClickListener {
            dismiss()
            val action = R.id.action_libraryFragment_to_editWordFragment
            findNavController().navigate(action)
        }
        binding.btnDictionaryAdd.setOnClickListener {
            dismiss()
            val action = R.id.action_libraryFragment_to_editDictionaryFragment
            findNavController().navigate(action)

        }
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}