package com.mcshr.wordloom.presentation.homeScreen.selectDictionary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mcshr.wordloom.databinding.ItemDictionarySelectableBinding
import com.mcshr.wordloom.domain.entities.Dictionary

class SelectableDictListAdapter :
    ListAdapter<Dictionary, SelectableDictViewHolder>(SelectableDictDiffCallback()) {

    var onSelectDictionary: ((Dictionary) -> Unit)? = null

    override fun onBindViewHolder(holder: SelectableDictViewHolder, position: Int) {
        val dictionary = getItem(position)

        holder.binding.textViewDictionaryName.text = dictionary.name
        holder.binding.textViewLanguages.text = String.format(
            "%s/%s",
            dictionary.languageOriginal.name,
            dictionary.languageTranslation.name
        )

        holder.binding.root.isSelected = dictionary.isSelected
        holder.binding.root.setOnClickListener {
            onSelectDictionary?.invoke(dictionary)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectableDictViewHolder {
        val binding = ItemDictionarySelectableBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SelectableDictViewHolder(binding)
    }
}