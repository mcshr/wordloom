package com.mcshr.wordloom.data.repository

import com.mcshr.wordloom.data.database.AppDatabase
import com.mcshr.wordloom.data.entities.mappers.toLanguageListDbModel
import com.mcshr.wordloom.domain.entities.Language
import com.mcshr.wordloom.domain.repository.PrepopulateDataRepository
import javax.inject.Inject

class PrepopulateDataRepositoryImpl @Inject constructor(
    database: AppDatabase
): PrepopulateDataRepository {
    private val dao = database.languageDao()

    override suspend fun prepopulateLanguages(languages: List<Language>) {
        dao.insertLanguageList(languages.toLanguageListDbModel())
    }

    override suspend fun hasLanguages(): Boolean {
        return dao.getLanguagesCount()>0
    }
}