package com.mcshr.wordloom.data.entities.tuples

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.mcshr.wordloom.data.entities.CardDbModel
import com.mcshr.wordloom.data.entities.CardTranslationDbModel
import com.mcshr.wordloom.data.entities.TranslationDbModel
import com.mcshr.wordloom.data.entities.UsageExampleDbModel

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
    val translations: List<WordWithTranslationRelation>,

    @Relation(
        entity = UsageExampleDbModel::class,
        parentColumn = "id",
        entityColumn = "card_id"
    )
    val usageExamples: List<UsageExampleDbModel>

)