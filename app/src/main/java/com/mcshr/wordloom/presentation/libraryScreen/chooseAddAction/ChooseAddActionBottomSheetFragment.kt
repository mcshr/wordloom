package com.mcshr.wordloom.presentation.libraryScreen.chooseAddAction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.FragmentChooseAddActionBottomSheetBinding

class ChooseAddActionBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentChooseAddActionBottomSheetBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentSelectAddBottomSheetBinding is null")

    private val viewModel by lazy{
        ViewModelProvider(this)[ChooseAddActionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseAddActionBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnCloseSheetAdd.setOnClickListener {
            dismiss()
        }
        viewModel.isAnyDictionaryExists.observe(viewLifecycleOwner){
            if(it){
                binding.btnWordAdd.isEnabled= true
                binding.btnWordAdd.setOnClickListener {
                    dismiss()
                    val action = R.id.action_libraryFragment_to_editWordFragment
                    findNavController().navigate(action)
                }
            }
            else{
                binding.btnWordAdd.isEnabled= false
                binding.btnWordAdd.setOnClickListener {
                    //showError()
                }
            }
        }

        binding.btnDictionaryAdd.setOnClickListener {
            dismiss()
            val action = R.id.action_libraryFragment_to_editDictionaryFragment
            findNavController().navigate(action)

        }
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}