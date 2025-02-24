package com.mcshr.wordloom.presentation.libraryScreen.chooseAddAction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.FragmentChooseAddActionBottomSheetBinding

class ChooseAddActionBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentChooseAddActionBottomSheetBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentSelectAddBottomSheetBinding is null")

    private val viewModel by viewModels<ChooseAddActionViewModel>()

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
        viewModel.isAnyDictionaryExists.observe(viewLifecycleOwner) { isAnyDictExists ->
            binding.btnWordAdd.apply {
                alpha = if(isAnyDictExists) 1f else 0.5f
                setOnClickListener {
                    if(isAnyDictExists){
                        dismiss()
                        val action = R.id.action_libraryFragment_to_editWordFragment
                        findNavController().navigate(action)
                    }else{
                        Snackbar.make(
                            it,
                            context.getString(R.string.warning_no_dictionary),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
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