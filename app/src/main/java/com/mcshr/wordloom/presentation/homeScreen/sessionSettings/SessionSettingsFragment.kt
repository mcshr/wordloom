package com.mcshr.wordloom.presentation.homeScreen.sessionSettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.FragmentSessionSettingsBinding


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

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
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
//            behavior.isFitToContents = false
//            behavior.expandedOffset = 100
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