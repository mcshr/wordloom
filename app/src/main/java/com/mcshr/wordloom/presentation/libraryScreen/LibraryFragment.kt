package com.mcshr.wordloom.presentation.libraryScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mcshr.wordloom.databinding.FragmentLibraryBinding
import com.mcshr.wordloom.presentation.SelectAddBottomSheetFragment

class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentLibraryBinding is null")

    private val viewModel by lazy {
        ViewModelProvider(this)[LibraryViewModel::class.java]
    }
    private val dictionaryAdapter = DictionaryLibListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnOpenSelectAdd.setOnClickListener{
            SelectAddBottomSheetFragment().show(parentFragmentManager, "SelectAddTag")
        }
        binding.rvDictionaryWithStatsList.adapter = dictionaryAdapter
        viewModel.allDictionaries.observe(viewLifecycleOwner){
            dictionaryAdapter.submitList(it)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}