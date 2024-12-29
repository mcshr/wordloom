package com.mcshr.wordloom.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RadioGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.ViewCustomRadioButtonBinding

class CustomRadioButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewCustomRadioButtonBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewCustomRadioButtonBinding.inflate(inflater, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRadioButton, defStyleAttr, 0)
            binding.radioButtonTitle.text = typedArray.getString(R.styleable.CustomRadioButton_title)
            binding.radioButtonSubtitle.text = typedArray.getString(R.styleable.CustomRadioButton_subtitle)
            typedArray.recycle()
        }

        setOnClickListener {
            if (!isChecked()) {
                setChecked(true)
            }
        }
    }

    private fun notifyRadioGroup() {
        val parent = parent
        if (parent is RadioGroup) {
            val currentId = id
            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)
                if (child is CustomRadioButton && child.id != currentId) {
                    child.setChecked(false)
                }
            }
            parent.check(currentId)
        }
    }

    fun setChecked(checked: Boolean) {
        binding.radioButton.isChecked = checked
        if(checked) notifyRadioGroup()
    }

    fun isChecked(): Boolean {
        return binding.radioButton.isChecked
    }
}
