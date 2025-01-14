package com.mcshr.wordloom.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "card_translation",
    foreignKeys = [
        ForeignKey(
            entity = CardDbModel::class,
            parentColumns = ["id"],
            childColumns = ["card_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TranslationDbModel::class,
            parentColumns = ["id"],
            childColumns = ["translation_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["card_id", "translation_id"],
    indices = [Index("translation_id")]
)
data class CardTranslationDbModel(
    @ColumnInfo(name = "card_id") val cardId: Long,
    @ColumnInfo(name = "translation_id") val translationId: Long
)
