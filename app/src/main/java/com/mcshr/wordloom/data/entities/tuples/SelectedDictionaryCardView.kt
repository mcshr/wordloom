package com.mcshr.wordloom.data.entities.tuples

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.mcshr.wordloom.data.entities.DictionaryCardDbModel
import com.mcshr.wordloom.domain.entities.WordStatus

@DatabaseView(
    viewName = "selected_dictionary_with_cards",
    value = """
        SELECT d.id AS dictionary_id, c.id AS card_id, c.status
        FROM dictionary d 
        JOIN dictionary_card dc ON d.id  = dc.dictionary_id
        JOIN card c ON dc.card_id = c.id 
        WHERE d.is_selected == 1
    """
)
data class SelectedDictionaryCardView(
    @Embedded val dictionaryCard: DictionaryCardDbModel,
    val status: WordStatus
)
