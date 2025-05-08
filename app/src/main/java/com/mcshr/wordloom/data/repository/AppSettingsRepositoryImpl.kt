package com.mcshr.wordloom.data.repository

import com.mcshr.wordloom.data.AppSettingsSharedPreferences
import com.mcshr.wordloom.domain.repository.AppSettingsRepository
import javax.inject.Inject

class AppSettingsRepositoryImpl @Inject constructor(
    private val dataSource: AppSettingsSharedPreferences
) : AppSettingsRepository {

    override fun saveSelectedDictionaryIdForWord(dictionaryId: Long) {
        dataSource.saveLastSelectedDictionaryId(dictionaryId)
    }

    override fun getLastSelectedDictionaryIdForWord(): Long? {
        return dataSource.getLastSelectedDictionaryId()
    }

    override fun getSessionWordLimit(): Int {
        return dataSource.getSessionWordLimit()
    }

    override fun saveSessionWordLimit(wordLimit: Int) {
        dataSource.saveSessionWordLimit(wordLimit)
    }
}