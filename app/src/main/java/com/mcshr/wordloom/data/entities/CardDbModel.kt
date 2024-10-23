package com.mcshr.wordloom.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "card",
    foreignKeys = [
        ForeignKey(
            entity = WordDbModel::class,
            parentColumns = ["id"],
            childColumns = ["word_id"]
        )
    ],
    indices = [Index("word_id")]
)
data class CardDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    //val status: WordStatus, //TODO create converter
    @ColumnInfo(
        name = "reviews_count",
        defaultValue = "0"
    ) val reviewsCount: Int,
    val nextRevDate: Long?,
    @ColumnInfo(name = "word_id") val wordId: Int,
    //val picture:... TODO
)
