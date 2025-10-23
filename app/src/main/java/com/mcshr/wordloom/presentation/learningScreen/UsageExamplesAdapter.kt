package com.mcshr.wordloom.presentation.learningScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mcshr.wordloom.databinding.ItemUsageExampleSmallBinding
import com.mcshr.wordloom.domain.entities.UsageExample

class UsageExamplesAdapter(): ListAdapter<UsageExample, UsageExampleViewHolder>(
    UsageExampleDiffCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsageExampleViewHolder {
        val binding = ItemUsageExampleSmallBinding.inflate(
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
        val item = getItem(position)
        with(holder.binding){
            tvExample.text = item.text
            tvTranslation.text = item.translation
        }
    }
}
class UsageExampleViewHolder(val binding: ItemUsageExampleSmallBinding):
    RecyclerView.ViewHolder(binding.root)
class UsageExampleDiffCallback(): DiffUtil.ItemCallback<UsageExample>() {
    override fun areItemsTheSame(
        oldItem: UsageExample,
        newItem: UsageExample
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: UsageExample,
        newItem: UsageExample
    ): Boolean {
        return true
    }

}