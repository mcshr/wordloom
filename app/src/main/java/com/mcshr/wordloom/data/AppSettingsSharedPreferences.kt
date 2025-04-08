package com.mcshr.wordloom.data

import android.content.Context

class AppSettingsSharedPreferences(context: Context) {
    private val sharedPrefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    //SELECTED DICTIONARY ID FOR WORD CREATION
    fun saveLastSelectedDictionaryId(dictId: Long) {
        sharedPrefs.edit().putLong(KEY_SELECTED_DICTIONARY_ID_FOR_WORD, dictId).apply()
    }

    fun getLastSelectedDictionaryId(): Long? {
        val dictId = sharedPrefs.getLong(KEY_SELECTED_DICTIONARY_ID_FOR_WORD, -1)
        return if (dictId == -1L) {
            null
        } else {
            dictId
        }
    }

    //SESSION WORD LIMIT
    fun saveSessionWordLimit(wordLimit:Int){
        sharedPrefs.edit().putInt(KEY_SESSION_WORD_LIMIT, wordLimit).apply()
    }
    fun getSessionWordLimit():Int{
        val wordLimit = sharedPrefs.getInt(KEY_SESSION_WORD_LIMIT, 10)
        return wordLimit
    }

    companion object {
        private const val NAME = "AppSettingsPrefs"
        private const val KEY_SELECTED_DICTIONARY_ID_FOR_WORD = "SelectedDictIdWord"
        private const val KEY_SESSION_WORD_LIMIT = "SessionWordLimit"
    }
}