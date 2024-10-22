package com.mcshr.wordloom.domain.entities

enum class WordStatus(id:Int) {
    UNKNOWN(1),
    KNOWN(2),
    READY_TO_LEARN(3),
    LEARNING(4),
    LEARNED(5)
}