package com.mcshr.wordloom.data.entities.tuples

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.mcshr.wordloom.data.entities.CardDbModel
import com.mcshr.wordloom.data.entities.DictionaryCardDbModel
import com.mcshr.wordloom.data.entities.DictionaryDbModel


data class DictionaryWithCardsRelation(
    @Embedded val dictionary: DictionaryDbModel,

    @Relation(
        entity = CardDbModel::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            parentColumn = "dictionary_id",
            entityColumn = "card_id",
            value = DictionaryCardDbModel::class
        )
    )
    val wordCardList: List<WordCardRelation>
    )