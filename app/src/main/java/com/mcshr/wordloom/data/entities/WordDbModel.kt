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
        Index(value = ["word_text", "language_id", "part_of_speech_code"], unique = true),
        Index("language_id"),
        Index("part_of_speech_code"),
    ]

)
data class WordDbModel(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "word_text") val wordText: String,
    @ColumnInfo(name = "language_id") val languageId: Long,
    @ColumnInfo(name = "part_of_speech_code") val partOfSpeechCode: String
)
