package com.mcshr.wordloom.presentation.dictionaryScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mcshr.wordloom.databinding.FragmentDictionaryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DictionaryFragment : Fragment() {

    private var _binding: FragmentDictionaryBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentDictionaryBinding is null")

//    private val args: DictionaryFragmentArgs by navArgs()
    private val viewModel by viewModels<DictionaryViewModel>()

    private val wordListAdapter = WordListAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDictionaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.dictionaryLiveData.observe(viewLifecycleOwner) {
            binding.toolbarDictionary.title = it.name
        }
        binding.toolbarDictionary.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        binding.rvCardsDictionaryList.adapter = wordListAdapter

        viewModel.wordList.observe(viewLifecycleOwner) {
            wordListAdapter.submitList(it)
        }

        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}