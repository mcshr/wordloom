package com.mcshr.wordloom.presentation.createWordScreen.selectPartOfSpeech

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mcshr.wordloom.databinding.ItemPartOfSpeechBinding
import com.mcshr.wordloom.domain.entities.PartOfSpeech
import com.mcshr.wordloom.presentation.utils.toResId

class PartOfSpeechAdapter(
    private val onItemClick:(PartOfSpeech)->Unit
): ListAdapter<PartOfSpeech, PartOfSpeechViewHolder>(
    PartOfSpeechDiffCallback()
) {
    private var selectedItem = PartOfSpeech.EMPTY
    fun selectItem(partOfSpeech: PartOfSpeech){
        selectedItem = partOfSpeech
        val prevItem = selectedItem
        selectedItem = partOfSpeech
        notifyItemChanged(currentList.indexOf(prevItem))
        notifyItemChanged(currentList.indexOf(selectedItem))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PartOfSpeechViewHolder {
       val binding = ItemPartOfSpeechBinding.inflate(
           LayoutInflater.from(parent.context),
           parent,
           false
       )
        return PartOfSpeechViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PartOfSpeechViewHolder,
        position: Int
    ){
        val item = getItem(position)
        val context = holder.binding.root.context
        holder.binding.tvPartOfSpeech.text =  context.getString(item.toResId())
        holder.binding.root.setOnClickListener {
            if(item != selectedItem) {
                onItemClick(item)
                selectItem(item)
            }
        }
        holder.binding.root.isSelected = item == selectedItem
    }
}
class PartOfSpeechViewHolder(val binding: ItemPartOfSpeechBinding): RecyclerView.ViewHolder(binding.root)

class PartOfSpeechDiffCallback(): DiffUtil.ItemCallback<PartOfSpeech>() {
    override fun areItemsTheSame(oldItem: PartOfSpeech, newItem: PartOfSpeech) = oldItem == newItem
    override fun areContentsTheSame(oldItem: PartOfSpeech, newItem: PartOfSpeech) = true
}

