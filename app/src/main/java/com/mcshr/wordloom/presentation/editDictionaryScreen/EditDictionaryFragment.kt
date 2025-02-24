package com.mcshr.wordloom.presentation.editDictionaryScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.FragmentEditDictionaryBinding

class EditDictionaryFragment : Fragment() {

    private var _binding: FragmentEditDictionaryBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentEditDictionaryBinding is null")

    private val viewModel: EditDictionaryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditDictionaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

//        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.toolbar.setOnMenuItemClickListener {
            option ->
                when(option.itemId){
                    R.id.menu_item_save -> {
                        saveDictionary()
                        true
                    }
                    else -> false//throw IllegalArgumentException("option.itemId such id doesnt exist")
                }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun saveDictionary(){
        val dictName = binding.editTextDictName.text.toString()
        viewModel.createDictionary(dictName)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}