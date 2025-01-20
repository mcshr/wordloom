package com.mcshr.wordloom.presentation.dictionaryScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mcshr.wordloom.databinding.FragmentDictionaryBinding

class DictionaryFragment : Fragment() {

    private var _binding : FragmentDictionaryBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentDictionaryBinding is null")

    private val args: DictionaryFragmentArgs by navArgs()

    private val viewModelFactory by lazy {
        DictionaryViewModel.provideFactory(
            requireActivity().application,
            args.dictionaryId
        )
    }
    private val viewModel by lazy{
        ViewModelProvider(this, viewModelFactory)[DictionaryViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDictionaryBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.dictionaryLiveData.observe(viewLifecycleOwner){
            binding.toolbarDictionary.title = it.name
        }
        binding.toolbarDictionary.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().popBackStack()
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}