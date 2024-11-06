package com.mcshr.wordloom.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mcshr.wordloom.data.WordStatusConverter
import com.mcshr.wordloom.domain.entities.WordStatus


@Entity(
    tableName = "card",
    foreignKeys = [
        ForeignKey(
            entity = WordDbModel::class,
            parentColumns = ["id"],
            childColumns = ["word_id"]
        )
    ],
    indices = [Index("word_id"), Index("status")]
)
data class CardDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @TypeConverters(WordStatusConverter::class)
    val status: WordStatus,
    @ColumnInfo(
        name = "reviews_count",
        defaultValue = "0"
    ) val reviewsCount: Int,
    @ColumnInfo(name = "next_rev_date")val nextRevDate: Long?,
    @ColumnInfo(name = "word_id") val wordId: Int,
    @ColumnInfo(name = "image_path") val imagePath: String?
)
