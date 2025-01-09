package com.mcshr.wordloom.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mcshr.wordloom.databinding.FragmentEditWordBinding

class EditWordFragment : Fragment() {

    private var _binding: FragmentEditWordBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentEditWordBinding is null")

    private val meaningList :MutableList<String> = mutableListOf("123", "qweqe")
    private val meaningAdapter = MeaningListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditWordBinding.inflate(inflater, container, false)


        binding.rvMeaningList.adapter = meaningAdapter
        binding.rvMeaningList.layoutManager = LinearLayoutManager(context)

        meaningAdapter.submitList(meaningList)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        binding.toolbar.setNavigationOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.toolbar.setOnClickListener {
            Toast.makeText(activity, "Text!", Toast.LENGTH_SHORT).show()
        }

        binding.btnAddMeaning.setOnClickListener {
            val meaning = binding.editTextMeaning.editableText.toString()
            if (meaning.isNotBlank()) {
                meaningList.add(meaning)
                meaningAdapter.submitList(meaningList.toList()) // Создать новый список
            }
        }



        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}