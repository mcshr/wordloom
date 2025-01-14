package com.mcshr.wordloom.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "translation",
    foreignKeys = [
        ForeignKey(
            entity = WordDbModel::class,
            parentColumns = ["id"],
            childColumns = ["word_id_original"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WordDbModel::class,
            parentColumns = ["id"],
            childColumns = ["word_id_translation"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["word_id_original", "word_id_translation"], unique = true),
        Index("word_id_translation")
    ]
)
data class TranslationDbModel(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "word_id_original") val wordIdOriginal: Long,
    @ColumnInfo(name = "word_id_translation") val wordIdTranslation: Long
)
