package com.mcshr.wordloom.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "dictionary")
data class DictionaryDbModel(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val name: String,
    val description: String?,
    //val icon: Icon?, TODO
    val creationDateTime: Long?
)
