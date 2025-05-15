package com.mcshr.wordloom.presentation.homeScreen.sessionSettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.FragmentSessionSettingsBinding
import com.mcshr.wordloom.presentation.homeScreen.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SessionSettingsFragment : BottomSheetDialogFragment() {

    private var _binding : FragmentSessionSettingsBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentSessionSettingsBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSessionSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
    private val viewModel: SessionSettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCloseSheet.setOnClickListener {
            dismiss()
        }

        val adapter = SessionViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false

        binding.optionLearnMode.setChecked(true)
        binding.viewPager.currentItem = 0


        binding.scrollView.smoothScrollTo(0, 0)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            binding.scrollView.smoothScrollTo(0, 0)
            val position = when(checkedId) {
                R.id.option_learn_mode -> 0
                R.id.option_swipe_mode -> 1
                else -> 0
            }
            binding.viewPager.currentItem = position
        }
        val bottomSheet = binding.root.parent as? View
        bottomSheet?.let {
            sheet ->
            val behavior = BottomSheetBehavior.from(sheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        binding.btnStartLearning.setOnClickListener{
            when (binding.radioGroup.checkedRadioButtonId){
                R.id.option_learn_mode -> {
                    viewModel.startLearning()
                    val action = HomeFragmentDirections.actionHomeFragmentToLearningFragment()
                    findNavController().navigate(action)
                }
                R.id.option_swipe_mode -> {}
            }
            dismiss()
        }
    }

    override fun getTheme(): Int {
        return R.style.WordloomBottomSheetDialog
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}