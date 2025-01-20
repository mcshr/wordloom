package com.mcshr.wordloom.presentation.libraryScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mcshr.wordloom.databinding.ItemDictionaryLibBinding
import com.mcshr.wordloom.domain.entities.Dictionary

class DictionaryLibListAdapter:ListAdapter<Dictionary, DictionaryLibViewHolder>(DictionaryLibDiffCallback()) {

    var openDictionary : ((dictionaryId: Long) -> Unit)? = null

    override fun onBindViewHolder(holder: DictionaryLibViewHolder, position: Int) {
        val dictionary = getItem(position)
        holder.binding.textViewDictionaryName.text = dictionary.name
        //TODO

        holder.binding.root.setOnClickListener {
            openDictionary?.invoke(dictionary.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryLibViewHolder {
        val binding = ItemDictionaryLibBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
            )
        return DictionaryLibViewHolder(binding)
    }
}