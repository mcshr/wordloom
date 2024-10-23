package com.mcshr.wordloom.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "word",
    foreignKeys = [
        ForeignKey(
            entity = LanguageDbModel::class,
            parentColumns = ["id"],
            childColumns = ["language_id"]
        )
    ],
    indices = [
        Index(value = ["word_text", "language_id", "part_of_speech_id"], unique = true),
        Index("language_id")
    ]

)
data class WordDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "word_text") val wordText: String,
    @ColumnInfo(name = "language_id") val languageId: Int,
    @ColumnInfo(name = "part_of_speech_id") val partOfSpeechId: Int //TODO part_of_speech
)
