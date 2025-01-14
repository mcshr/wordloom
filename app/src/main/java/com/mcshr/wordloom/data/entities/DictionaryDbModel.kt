package com.mcshr.wordloom.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "dictionary")
data class DictionaryDbModel(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val description: String?,
    @ColumnInfo(name = "is_selected", defaultValue = "false") val isSelected: Boolean,
    @ColumnInfo(name = "creation_date_time") val creationDateTime: Long?,
    @ColumnInfo(name = "image_path") val imagePath: String?
)
