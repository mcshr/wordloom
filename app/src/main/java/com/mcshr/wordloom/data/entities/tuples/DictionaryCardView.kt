package com.mcshr.wordloom.data.entities.tuples

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.mcshr.wordloom.data.entities.CardDbModel
import com.mcshr.wordloom.data.entities.DictionaryDbModel

@DatabaseView(
    viewName = "dictionary_with_cards",
    value = """
        SELECT d.*, c.* 
        FROM dictionary d 
        JOIN dictionary_card dc ON d.id = dc.dictionary_id 
        JOIN card c ON dc.card_id = c.id 
        WHERE d.is_selected == 1
    """
)
data class DictionaryCardView(
    @Embedded val dictionary: DictionaryDbModel,
    @Embedded val card: CardDbModel
)
