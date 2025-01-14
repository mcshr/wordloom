package com.mcshr.wordloom.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "language")
data class LanguageDbModel(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val code: String
)