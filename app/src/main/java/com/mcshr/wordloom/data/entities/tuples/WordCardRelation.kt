package com.mcshr.wordloom.data.entities.tuples

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.mcshr.wordloom.data.entities.CardDbModel
import com.mcshr.wordloom.data.entities.CardTranslationDbModel
import com.mcshr.wordloom.data.entities.TranslationDbModel

data class WordCardRelation(
    @Embedded val card: CardDbModel,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = CardTranslationDbModel::class,
            parentColumn = "card_id",
            entityColumn = "translation_id"
        ),
        entity = TranslationDbModel::class
    )
    val translations: List<WordWithTranslationRelation>
)