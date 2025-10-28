package com.mcshr.wordloom.data.entities.tuples

import androidx.room.Embedded

data class DictionaryWithStatsTuple(
    @Embedded val dictionary: DictionaryRelation,
    val total: Int,
    val unknown: Int,
    val known: Int,
    val readyToLearn: Int,
    val learning: Int,
    val learned: Int,
    val progress: Int
)
