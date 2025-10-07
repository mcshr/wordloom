package com.mcshr.wordloom.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "dictionary_card",
    primaryKeys = ["dictionary_id", "card_id"],
    indices = [
        Index("card_id"),
        Index(value = ["dictionary_id", "card_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = DictionaryDbModel::class,
            parentColumns = ["id"],
            childColumns = ["dictionary_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CardDbModel::class,
            parentColumns = ["id"],
            childColumns = ["card_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DictionaryCardDbModel(
    @ColumnInfo(name = "dictionary_id") val dictionaryId: Long,
    @ColumnInfo(name = "card_id") val cardId: Long
)
