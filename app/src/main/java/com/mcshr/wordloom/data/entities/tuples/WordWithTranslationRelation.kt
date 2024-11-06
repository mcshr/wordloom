package com.mcshr.wordloom.data.entities.tuples

import androidx.room.Embedded
import androidx.room.Relation
import com.mcshr.wordloom.data.entities.TranslationDbModel
import com.mcshr.wordloom.data.entities.WordDbModel

data class WordWithTranslationRelation(
    @Embedded val translationDbModel: TranslationDbModel,

    @Relation(
        parentColumn = "word_id_original",
        entityColumn = "id"
    )
    val wordOriginal: WordDbModel,

    @Relation(
        parentColumn = "word_id_translation",
        entityColumn = "id"
    )
    val wordTranslation: WordDbModel
)