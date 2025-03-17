package com.mcshr.wordloom.data.entities.tuples

import androidx.room.Embedded
import androidx.room.Relation
import com.mcshr.wordloom.data.entities.TranslationDbModel
import com.mcshr.wordloom.data.entities.WordDbModel

data class WordWithTranslationRelation(
    @Embedded val translationDbModel: TranslationDbModel,

    @Relation(
        parentColumn = "word_id_original",
        entityColumn = "id",
        entity = WordDbModel::class
    )
    val wordOriginal: WordRelation,

    @Relation(
        parentColumn = "word_id_translation",
        entityColumn = "id",
        entity = WordDbModel::class
    )
    val wordTranslation: WordRelation
)