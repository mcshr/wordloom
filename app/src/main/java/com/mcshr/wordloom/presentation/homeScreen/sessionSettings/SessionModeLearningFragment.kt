package com.mcshr.wordloom.presentation.homeScreen.sessionSettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mcshr.wordloom.databinding.FragmentSessionModeLearningBinding


class SessionModeLearningFragment : Fragment() {

    private var _binding: FragmentSessionModeLearningBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentSessionModeLearningBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSessionModeLearningBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
    companion object{
        fun newInstance() = SessionModeLearningFragment()
    }
}