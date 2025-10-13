package com.mcshr.wordloom.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "usage_example",
    foreignKeys = [
        ForeignKey(
            entity = CardDbModel::class,
            parentColumns = ["id"],
            childColumns = ["card_id"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ],
    indices = [Index("card_id")]

)
data class UsageExampleDbModel(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "card_id") val cardId: Long,
    @ColumnInfo(name = "example_text") val exampleText: String,
    @ColumnInfo(name = "example_text_translation") val exampleTextTranslation: String? = null,
)
