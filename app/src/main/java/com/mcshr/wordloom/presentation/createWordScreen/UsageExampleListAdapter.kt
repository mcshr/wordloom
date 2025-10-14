package com.mcshr.wordloom.presentation.createWordScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mcshr.wordloom.databinding.ItemUsageExampleBinding
import com.mcshr.wordloom.presentation.utils.uiModels.UsageExampleUiModel

class UsageExampleListAdapter(
    private val onTextChangedExample: (UsageExampleUiModel, String) -> Unit,
    private val onTextChangedTranslation: (UsageExampleUiModel, String) -> Unit,
    private val onDelete: (UsageExampleUiModel) -> Unit
) : ListAdapter<UsageExampleUiModel, UsageExampleViewHolder>(UsageExampleDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsageExampleViewHolder {
        val binding = ItemUsageExampleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UsageExampleViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: UsageExampleViewHolder,
        position: Int
    ) {
        val currentExample = getItem(position)
        holder.binding.btnDeleteExample.setOnClickListener {
            onDelete(currentExample)
        }

        holder.binding.etExample.setText(currentExample.text)
        holder.binding.etTranslation.setText(currentExample.translation)


        holder.binding.etExample.doAfterTextChanged {
            val newText = it?.toString()?:""
            onTextChangedExample(currentExample, newText)
        }
        holder.binding.etTranslation.doAfterTextChanged {
            val newText = it?.toString()?:""
            onTextChangedTranslation(currentExample, newText)
        }
    }

}

class UsageExampleViewHolder(val binding: ItemUsageExampleBinding) :
    RecyclerView.ViewHolder(binding.root)

class UsageExampleDiffCallback(): DiffUtil.ItemCallback<UsageExampleUiModel>() {
    override fun areItemsTheSame(
        oldItem: UsageExampleUiModel,
        newItem: UsageExampleUiModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UsageExampleUiModel,
        newItem: UsageExampleUiModel
    ): Boolean {
        return true
    }
}