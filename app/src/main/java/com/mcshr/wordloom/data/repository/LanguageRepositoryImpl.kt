package com.mcshr.wordloom.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mcshr.wordloom.data.database.AppDatabase
import com.mcshr.wordloom.data.entities.mappers.LanguageMapper
import com.mcshr.wordloom.domain.entities.Language
import com.mcshr.wordloom.domain.repository.LanguageRepository

class LanguageRepositoryImpl(private val application: Application):LanguageRepository {
    private val db = AppDatabase.getInstance(application)
    private val dao = db.languageDao()

    override fun getAllLanguages(): LiveData<List<Language>> {
        return dao.getAllLanguages().map { it.map{ LanguageMapper.mapToDomainEntity(it)} }
    }
}