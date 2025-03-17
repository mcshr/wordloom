package com.mcshr.wordloom.data.entities.tuples

import androidx.room.Embedded
import androidx.room.Relation
import com.mcshr.wordloom.data.entities.DictionaryDbModel
import com.mcshr.wordloom.data.entities.LanguageDbModel

data class DictionaryRelation(
    @Embedded val dictionary: DictionaryDbModel,

    @Relation(
        parentColumn = "language_id_original",
        entityColumn = "id"
    ) val languageOriginal: LanguageDbModel,

    @Relation(
        parentColumn = "language_id_translation",
        entityColumn = "id"
    ) val languageTranslation: LanguageDbModel

)