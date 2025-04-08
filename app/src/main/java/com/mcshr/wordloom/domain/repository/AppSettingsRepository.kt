package com.mcshr.wordloom.domain.repository

interface AppSettingsRepository {
    fun saveSelectedDictionaryIdForWord(dictionaryId: Long)
    fun getLastSelectedDictionaryIdForWord(): Long?

    fun getSessionWordLimit():Int
    fun saveSessionWordLimit(wordLimit:Int)
}