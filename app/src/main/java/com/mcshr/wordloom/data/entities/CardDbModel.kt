package com.mcshr.wordloom.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mcshr.wordloom.data.database.converters.WordStatusConverter
import com.mcshr.wordloom.domain.entities.WordStatus


@Entity(
    tableName = "card",
    indices = [Index("status")]
)
data class CardDbModel(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @TypeConverters(WordStatusConverter::class)
    val status: WordStatus,
    @ColumnInfo(
        name = "reviews_count",
        defaultValue = "0"
    ) val reviewsCount: Int,
    @ColumnInfo(name = "next_rev_date")val nextRevDate: Long?,
    @ColumnInfo(name = "image_path") val imagePath: String?
)
