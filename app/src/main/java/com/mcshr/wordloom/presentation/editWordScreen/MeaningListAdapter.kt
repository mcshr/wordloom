package com.mcshr.wordloom.presentation.editWordScreen
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mcshr.wordloom.databinding.ItemEditWordMeaningBinding

class MeaningListAdapter: ListAdapter<String,MeaningViewHolder>(
    MeaningDiffCallback()
) {
    var deleteMeaning: ((String) ->Unit)? = null
    var updateMeaning: ((String) ->Unit)? = null

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
       val binding = holder.binding
        val meaning = getItem(position)
        binding.tvMeaning.text = meaning
        binding.btnDeleteMeaning.setOnClickListener {
            deleteMeaning?.invoke(meaning)
        }
        binding.cardView.setOnClickListener {
            updateMeaning?.invoke(meaning )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding = ItemEditWordMeaningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeaningViewHolder(binding)
    }

}


