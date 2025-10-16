package com.mcshr.wordloom.presentation.wordCardScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mcshr.wordloom.R
import com.mcshr.wordloom.databinding.ItemUsageExampleBinding
import com.mcshr.wordloom.domain.entities.UsageExample

class UsageExampleInfoListAdapter() : ListAdapter<UsageExample, UsageExampleInfoViewHolder>(
    UsageExampleInfoDiffCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsageExampleInfoViewHolder {
        val binding = ItemUsageExampleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UsageExampleInfoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: UsageExampleInfoViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        val context = holder.binding.root.context
        with(holder.binding) {
            if (item.translation.isNullOrBlank()) {
                tvTranslation.visibility = View.GONE
            } else {
                tvExample.visibility = View.VISIBLE
                tvTranslation.text = item.translation
            }
            tvExample.text = item.text
            tvTitle.text = context.getString(
                R.string.usage_example_title,
                position + 1
            )
        }


    }
}

class UsageExampleInfoViewHolder(val binding: ItemUsageExampleBinding) :
    RecyclerView.ViewHolder(binding.root)

class UsageExampleInfoDiffCallback : DiffUtil.ItemCallback<UsageExample>() {
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
        return oldItem == newItem
    }
}