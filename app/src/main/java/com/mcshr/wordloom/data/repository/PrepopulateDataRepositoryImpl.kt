package com.mcshr.wordloom.data.repository

import android.app.Application
import com.mcshr.wordloom.data.database.AppDatabase
import com.mcshr.wordloom.data.entities.mappers.LanguageMapper
import com.mcshr.wordloom.domain.entities.Language
import com.mcshr.wordloom.domain.repository.PrepopulateDataRepository

class PrepopulateDataRepositoryImpl(application: Application): PrepopulateDataRepository {
    private val db = AppDatabase.getInstance(application)
    private val dao = db.prepopulateDataDao()
    private val langMapper = LanguageMapper()

    override suspend fun prepopulateLanguages(languages: List<Language>) {
        dao.insertLanguageList(langMapper.mapEntityListToDbModelList(languages))
    }
}