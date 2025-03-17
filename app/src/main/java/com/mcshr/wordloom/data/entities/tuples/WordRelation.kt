package com.mcshr.wordloom.data.entities.tuples

import androidx.room.Embedded
import androidx.room.Relation
import com.mcshr.wordloom.data.entities.LanguageDbModel
import com.mcshr.wordloom.data.entities.WordDbModel

data class WordRelation(
    @Embedded val word: WordDbModel,

    @Relation(
        parentColumn = "language_id",
        entityColumn = "id"
    )
    val language: LanguageDbModel
)