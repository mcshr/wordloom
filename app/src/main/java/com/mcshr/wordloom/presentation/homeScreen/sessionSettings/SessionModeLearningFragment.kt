package com.mcshr.wordloom.presentation.homeScreen.sessionSettings

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.slider.Slider
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.FragmentSessionModeLearningBinding
import com.mcshr.wordloom.presentation.homeScreen.HomeViewModel
import java.util.Locale


class SessionModeLearningFragment : Fragment() {

    private var _binding: FragmentSessionModeLearningBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentSessionModeLearningBinding is null")

    private val sharedViewModel: HomeViewModel by activityViewModels()
    private val viewModel: SessionSettingsViewModel by viewModels(
        { requireParentFragment() }
    )

    private val sliderListener = Slider.OnChangeListener { _, value, _ ->
        viewModel.setWordLimit(value.toInt())
    }
    private val editTextWordLimitListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            val value = (s.toString().toIntOrNull() ?: 0)
            viewModel.setWordLimit(value)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSessionModeLearningBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedViewModel.stats.observe(viewLifecycleOwner) {
            val total = it.readyToLearn + (sharedViewModel.repeatCount.value ?: 0)
            binding.tvTotalCountAvailable.text = total.toString()
            binding.tvToLearnCountAvailable.text = it.readyToLearn.toString()
        }
        sharedViewModel.repeatCount.observe(viewLifecycleOwner) {
            val total = it + (sharedViewModel.stats.value?.readyToLearn ?: 0)
            binding.tvTotalCountAvailable.text = total.toString()
            binding.tvToRepeatCountAvailable.text = it.toString()
        }

        binding.slider.setLabelFormatter { value ->
            String.format(Locale.getDefault(), "%d", value.toInt())
        }

        binding.slider.addOnChangeListener(sliderListener)
        binding.editTextWordLimit.addTextChangedListener(editTextWordLimitListener)

        updateWordLimit(viewModel.getSessionWordLimit())

        viewModel.wordLimit.observe(viewLifecycleOwner) {
            updateWordLimit(it)
        }


        binding.btnAutoAdd.setOnClickListener {
            viewModel.autoAddCards()

        }
        binding.btnManageCards.setOnClickListener {
            viewModel.saveSessionWordLimit()
            findNavController().navigate(R.id.wordsSelectionFragment)
            (parentFragment as BottomSheetDialogFragment).dismiss()
        }


        super.onViewCreated(view, savedInstanceState)
    }

    private fun updateWordLimit(value: Int) {
        if (value.toString() != binding.editTextWordLimit.text.toString()) {
            binding.editTextWordLimit.removeTextChangedListener(editTextWordLimitListener)
            binding.slider.removeOnChangeListener(sliderListener)

            binding.slider.value = value.coerceIn(10, 100).toFloat()
            binding.editTextWordLimit.setText(value.toString())

            binding.slider.addOnChangeListener(sliderListener)
            binding.editTextWordLimit.addTextChangedListener(editTextWordLimitListener)
        } else {
            binding.slider.removeOnChangeListener(sliderListener)
            binding.slider.value = value.coerceIn(10, 100).toFloat()
            binding.slider.addOnChangeListener(sliderListener)
        }
        binding.wordLimitHint.text = getString(
            R.string.word_limit_hint_text, value
        )
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = SessionModeLearningFragment()
    }
}