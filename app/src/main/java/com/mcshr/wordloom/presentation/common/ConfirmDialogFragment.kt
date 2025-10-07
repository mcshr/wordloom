package com.mcshr.wordloom.presentation.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.FragmentConfirmDialogBinding


class ConfirmDialogFragment : DialogFragment() {

    private var _binding: FragmentConfirmDialogBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException(
            " FragmentConfirmDialogDialogBinding is null"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.WordloomDialog)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConfirmDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = requireArguments().getString(TITLE)
        val message = requireArguments().getString(MESSAGE)
        val confirmText = requireArguments().getString(CONFIRM_TEXT)
        val requestKey = requireArguments().getString(REQUEST_KEY)

        binding.tvTitle.text = title
        binding.tvMessage.text = message

        binding.btnConfirm.text = confirmText
        binding.btnConfirm.setOnClickListener {
            parentFragmentManager.setFragmentResult(
                requestKey ?: "",
                Bundle().apply {
                    putBoolean(KEY_CONFIRMED, true)
                }
            )
            dismiss()
        }

        binding.btnCancel.setOnClickListener { dismiss() }


    }

    companion object {
        private const val TITLE = "title"
        private const val MESSAGE = "message"
        private const val CONFIRM_TEXT = "confirmText"
        private const val REQUEST_KEY = "requestKey"
        const val KEY_CONFIRMED = "confirmed"

        fun newInstance(
            title: String,
            message: String,
            confirmText: String,
            requestKey: String
        ) = ConfirmDialogFragment().apply {
            arguments = Bundle().apply {
                putString(TITLE, title)
                putString(MESSAGE, message)
                putString(CONFIRM_TEXT, confirmText)
                putString(REQUEST_KEY, requestKey)
            }
        }
    }
}