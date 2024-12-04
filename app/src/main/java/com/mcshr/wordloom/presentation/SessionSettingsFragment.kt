package com.mcshr.wordloom.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mcshr.wordloom.databinding.FragmentSessionSettingsBinding


class SessionSettingsFragment : BottomSheetDialogFragment() {

    private var _binding : FragmentSessionSettingsBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentSessionSettingsBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSessionSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.optionLearnMode.setOnClickListener {
            binding.radioButtonLearnMode.isChecked = true
            binding.radioButtonSwipeMode.isChecked = false
        }
        binding.optionSwipeMode.setOnClickListener {
            binding.radioButtonSwipeMode.isChecked = true
            binding.radioButtonLearnMode.isChecked = false
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}