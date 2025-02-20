package com.mcshr.wordloom.data.repository

import android.app.Application
import com.mcshr.wordloom.data.AppSettingsSharedPreferences
import com.mcshr.wordloom.domain.repository.AppSettingsRepository

class AppSettingsRepositoryImpl(application: Application):AppSettingsRepository {
    private val dataSource = AppSettingsSharedPreferences(application)

    override fun saveSelectedDictionaryIdForWord(dictionaryId: Long) {
        dataSource.saveLastSelectedDictionaryId(dictionaryId)
    }

    override fun getLastSelectedDictionaryIdForWord(): Long? {
        return dataSource.getLastSelectedDictionaryId()
    }
}