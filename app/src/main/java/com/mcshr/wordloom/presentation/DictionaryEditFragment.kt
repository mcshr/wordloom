package com.mcshr.wordloom.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mcshr.wordloom.databinding.FragmentDictionaryEditBinding

class DictionaryEditFragment : Fragment() {

    private var _binding: FragmentDictionaryEditBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentDictionaryEdit is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDictionaryEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}