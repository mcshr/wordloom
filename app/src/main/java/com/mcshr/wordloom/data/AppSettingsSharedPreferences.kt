package com.mcshr.wordloom.data

import android.content.Context

class AppSettingsSharedPreferences(context: Context) {
    private val sharedPrefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

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

    companion object {
        private const val NAME = "AppSettingsPrefs"
        private const val KEY_SELECTED_DICTIONARY_ID_FOR_WORD = "SelectedDictIdWord"
    }
}