package com.mcshr.wordloom.domain.entities

enum class PartOfSpeech (val code: String) {
    EMPTY("empty"),
    NOUN("noun"),
    VERB("verb"),
    ADJECTIVE("adjective"),
    ADVERB("adverb"),
    OTHER("other");

    companion object {
        fun fromCode(code: String) = entries.find { it.code == code } ?: EMPTY
    }
}