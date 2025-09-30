package com.mcshr.wordloom.presentation.dictionaryScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mcshr.wordloom.databinding.FragmentDictionaryBinding
import com.mcshr.wordloom.presentation.utils.setDebounceOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DictionaryFragment : Fragment() {

    private var _binding: FragmentDictionaryBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentDictionaryBinding is null")

    private val viewModel by viewModels<DictionaryViewModel>()

    private val wordListAdapter = WordListAdapter { wordCard ->
        WordMenuDialogFragment.newInstance(wordCard).show(
            childFragmentManager,
            "DictMenu"
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDictionaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupWordListAdapter()

        binding.btnAddNewWord.setDebounceOnClickListener{
            viewModel.selectDictionaryToAddWord()
            val action = DictionaryFragmentDirections
                .actionDictionaryFragmentToEditWordFragment()
            findNavController().navigate(action)
        }

    }

    private fun setupWordListAdapter() {
        binding.rvCardsDictionaryList.adapter = wordListAdapter
        viewModel.wordList.observe(viewLifecycleOwner) {
            wordListAdapter.submitList(it)
        }
    }

    private fun setupToolbar() {
        viewModel.dictionaryLiveData.observe(viewLifecycleOwner) {
            binding.toolbarDictionary.title = it.name
        }

        binding.toolbarDictionary.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}