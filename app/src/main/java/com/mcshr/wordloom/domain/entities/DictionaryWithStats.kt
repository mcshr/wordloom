package com.mcshr.wordloom.domain.entities

data class DictionaryWithStats(
    val dictionary: Dictionary,
    val totalCountCards: Int,
    val unknownCountCards: Int,
    val knownCountCards: Int,
    val readyToLearnCountCards: Int,
    val learningCountCards: Int,
    val learnedCountCards: Int,
    val learningProgress:Int
)
