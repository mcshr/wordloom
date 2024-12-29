package com.mcshr.wordloom.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mcshr.wordloom.databinding.FragmentSessionModeSwipeBinding


class SessionModeSwipeFragment : Fragment() {

    private var _binding: FragmentSessionModeSwipeBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentSessionModeSwipeBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSessionModeSwipeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
    companion object{
        fun newInstance() = SessionModeSwipeFragment()
    }
}