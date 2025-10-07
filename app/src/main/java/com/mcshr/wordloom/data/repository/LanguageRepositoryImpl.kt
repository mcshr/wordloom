package com.mcshr.wordloom.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mcshr.wordloom.data.database.AppDatabase
import com.mcshr.wordloom.data.mappers.toDomainEntity
import com.mcshr.wordloom.domain.entities.Language
import com.mcshr.wordloom.domain.repository.LanguageRepository
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    database: AppDatabase
):LanguageRepository {
    private val dao = database.languageDao()

    override fun getAllLanguages(): LiveData<List<Language>> {
        return dao.getAllLanguages().map { list -> list.map{ it.toDomainEntity()} }
    }
    override suspend fun getLanguagesCount(): Int {
        return dao.getLanguagesCount()
    }
}