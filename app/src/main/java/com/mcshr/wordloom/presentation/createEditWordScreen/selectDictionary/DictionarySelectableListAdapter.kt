package com.mcshr.wordloom.presentation.createEditWordScreen.selectDictionary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mcshr.wordloom.databinding.ItemDictionarySelectableBinding
import com.mcshr.wordloom.domain.entities.Dictionary

class DictionarySelectableListAdapter
    : ListAdapter<Dictionary, DictionarySelectableViewHolder>(
    DictionarySelectableDiffCallback()
) {

    private var selectedDictionaryId: Long? = null
    var onSelectDictionary: ((Dictionary) -> Unit)? = null


    fun selectDictionary(dictId:Long){
        val previousDictId = selectedDictionaryId
        selectedDictionaryId = dictId
        val previousIndex = currentList.indexOfFirst { it.id == previousDictId }
        val currentIndex = currentList.indexOfFirst { it.id == selectedDictionaryId }
        if(previousIndex != -1)
            notifyItemChanged(previousIndex)
        notifyItemChanged(currentIndex)
    }

    override fun onBindViewHolder(holder: DictionarySelectableViewHolder, position: Int) {
        val dictionary = getItem(position)
        holder.binding.textViewDictionaryName.text = dictionary.name
        holder.binding.textViewLanguages.text = String.format(
            "%s/%s",
            dictionary.languageOriginal.name,
            dictionary.languageTranslation.name
        )

        val isSelected = dictionary.id == selectedDictionaryId
        holder.binding.root.isSelected = isSelected

        holder.binding.root.setOnClickListener {
            if (selectedDictionaryId != dictionary.id) {
                selectDictionary(dictionary.id)
                onSelectDictionary?.invoke(dictionary)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DictionarySelectableViewHolder {

        val binding = ItemDictionarySelectableBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DictionarySelectableViewHolder(binding)
    }
}