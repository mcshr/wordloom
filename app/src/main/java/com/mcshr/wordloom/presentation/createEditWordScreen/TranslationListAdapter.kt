package com.mcshr.wordloom.presentation.createEditWordScreen
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mcshr.wordloom.databinding.ItemEditWordTranslationBinding

class TranslationListAdapter: ListAdapter<String,TranslationViewHolder>(
    TranslationDiffCallback()
) {
    var deleteTranslation: ((String) ->Unit)? = null
    var updateTranslation: ((String) ->Unit)? = null

    override fun onBindViewHolder(holder: TranslationViewHolder, position: Int) {
       val binding = holder.binding
        val meaning = getItem(position)
        binding.tvMeaning.text = meaning
        binding.btnDeleteMeaning.setOnClickListener {
            deleteTranslation?.invoke(meaning)
        }
        binding.cardView.setOnClickListener {
            updateTranslation?.invoke(meaning )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranslationViewHolder {
        val binding = ItemEditWordTranslationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TranslationViewHolder(binding)
    }

}
class TranslationViewHolder(val binding: ItemEditWordTranslationBinding) :
    RecyclerView.ViewHolder(binding.root)

class TranslationDiffCallback:DiffUtil.ItemCallback<String>() {
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}


