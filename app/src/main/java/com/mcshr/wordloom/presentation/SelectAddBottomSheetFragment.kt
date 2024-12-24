package com.mcshr.wordloom.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}