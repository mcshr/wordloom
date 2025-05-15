package com.mcshr.wordloom.presentation.learningScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mcshr.wordloom.databinding.FragmentLearningBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LearningFragment : Fragment() {

    private var _binding: FragmentLearningBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("Fragment Learning binding is null")

    private val viewModel: LearningViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLearningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.learningSet.observe(viewLifecycleOwner) {
            Log.d("LISTTTT", it.toString())
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().popBackStack()
        }
    }
}