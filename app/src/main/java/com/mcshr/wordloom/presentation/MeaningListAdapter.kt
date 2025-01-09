package com.mcshr.wordloom.presentation
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mcshr.wordloom.databinding.ItemEditWordMeaningBinding

class MeaningListAdapter: ListAdapter<String, MeaningListAdapter.MeaningViewHolder>(MeaningDiffCallback()) {
    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
       val binding = holder.binding
        binding.tvMeaning.text = getItem(position)
        binding.btnDeleteMeaning.setOnClickListener {
            //TODO()
        }
        binding.cardView.setOnClickListener {
            //TODO()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding = ItemEditWordMeaningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeaningViewHolder(binding)
    }
    class MeaningViewHolder(val binding: ItemEditWordMeaningBinding) :RecyclerView.ViewHolder(binding.root)
}


