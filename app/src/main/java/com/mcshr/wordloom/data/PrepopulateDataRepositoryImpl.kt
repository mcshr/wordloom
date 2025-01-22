package com.mcshr.wordloom.data

import android.app.Application
import com.mcshr.wordloom.data.entities.mappers.LanguageMapper
import com.mcshr.wordloom.domain.PrepopulateDataRepository
import com.mcshr.wordloom.domain.entities.Language

class PrepopulateDataRepositoryImpl(application: Application): PrepopulateDataRepository {
    private val db = AppDatabase.getInstance(application)
    private val dao = db.prepopulateDataDao()
    private val langMapper = LanguageMapper()

    override suspend fun prepopulateLanguages(languages: List<Language>) {
        dao.insertLanguageList(langMapper.mapEntityListToDbModelList(languages))
    }
}