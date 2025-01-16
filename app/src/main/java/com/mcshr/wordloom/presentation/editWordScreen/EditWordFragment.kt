package com.mcshr.wordloom.presentation.editWordScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mcshr.wordloom.databinding.FragmentEditWordBinding

class EditWordFragment : Fragment() {

    private var _binding: FragmentEditWordBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentEditWordBinding is null")

    private val viewModel: EditWordViewModel by lazy {
        ViewModelProvider(this)[EditWordViewModel::class.java]
    }
    private val meaningAdapter = MeaningListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.toolbar.setOnClickListener {
            Toast.makeText(activity, "Text!", Toast.LENGTH_SHORT).show() //TODO dictionary selection
        }

        binding.rvMeaningList.adapter = meaningAdapter
        meaningAdapter.deleteMeaning = { meaning  -> viewModel.deleteMeaning(meaning ) }
        meaningAdapter.updateMeaning =
            { meaning  -> binding.editTextMeaning.setText(viewModel.deleteMeaning(meaning)) }
        viewModel.meaningList.observe(viewLifecycleOwner) {
            meaningAdapter.submitList(it)
        }

        binding.btnAddMeaning.setOnClickListener {
            val meaning = binding.editTextMeaning.editableText.toString()
            if(viewModel.addMeaning(meaning))
                binding.editTextMeaning.editableText.clear()
            else{
            //TODO error message
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}