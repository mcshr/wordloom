package com.mcshr.wordloom.presentation.homeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mcshr.wordloom.databinding.FragmentHomeBinding
import com.mcshr.wordloom.presentation.homeScreen.selectDictionary.SelectDictionaryBottomSheet
import com.mcshr.wordloom.presentation.homeScreen.sessionSettings.SessionSettingsFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnMainLearn.setOnClickListener {
            SessionSettingsFragment().show(parentFragmentManager,"SessionSettingsTag")
        }

        binding.btnAddMoreDicts.setOnClickListener {
            SelectDictionaryBottomSheet().show(parentFragmentManager, "AddSelectableDictionaryTag")
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}