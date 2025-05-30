package com.mcshr.wordloom.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mcshr.wordloom.databinding.FragmentProgressBinding

class ProgressFragment : Fragment() {

    private var _binding:FragmentProgressBinding? = null
    private val binding
        get() = _binding?:throw RuntimeException("FragmentProgressBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}