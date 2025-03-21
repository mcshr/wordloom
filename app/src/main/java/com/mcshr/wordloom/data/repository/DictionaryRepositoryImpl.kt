package com.mcshr.wordloom.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mcshr.wordloom.data.database.AppDatabase
import com.mcshr.wordloom.data.entities.mappers.toDBModel
import com.mcshr.wordloom.data.entities.mappers.toDictionaryListDomain
import com.mcshr.wordloom.data.entities.mappers.toDictionaryWithStatsListDomain
import com.mcshr.wordloom.data.entities.mappers.toDomainEntity
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.entities.DictionaryWithStats
import com.mcshr.wordloom.domain.repository.DictionaryRepository

class DictionaryRepositoryImpl(application: Application) : DictionaryRepository {
    private val db = AppDatabase.getInstance(application)
    private val dao = db.dictionaryDao()

    override suspend fun createDictionary(dictionary: Dictionary): Boolean {
        dao.getDictionaryByName(dictionary.name)?.let {
            return false
        }
        dao.createDictionary(dictionary.toDBModel())

        return true
    }

    override fun editDictionary(dictionary: Dictionary) {
        TODO("Not yet implemented")
    }

    override fun deleteDictionary(dictionary: Dictionary) {
        TODO("Not yet implemented")
    }

    override suspend fun getDictionary(dictionaryId: Long): Dictionary {
        return dao.getDictionaryById(dictionaryId).toDomainEntity()
    }

    override fun getAllDictionaries(): LiveData<List<Dictionary>> {
        return dao.getAllDictionaries().map { list ->
            list.toDictionaryListDomain()
        }
    }

    override fun getAllDictionariesWithStats(): LiveData<List<DictionaryWithStats>> {
        return dao.getAllDictWithStats().map { it.toDictionaryWithStatsListDomain() }
    }

    override suspend fun getLastCreatedDictionary(): Dictionary? {
        return dao.getLastCreatedDictionary()?.toDomainEntity()
    }

    override suspend fun isAnyDictionaryExists(): Boolean {
        return dao.getLastCreatedDictionary() != null
    }
}