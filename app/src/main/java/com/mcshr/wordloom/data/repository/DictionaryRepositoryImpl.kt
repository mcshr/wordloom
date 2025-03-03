package com.mcshr.wordloom.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mcshr.wordloom.data.database.AppDatabase
import com.mcshr.wordloom.data.entities.mappers.DictionaryMapper
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.repository.DictionaryRepository

class DictionaryRepositoryImpl(application: Application): DictionaryRepository {
    private val db = AppDatabase.getInstance(application)
    private val dao = db.dictionaryDao()

    override suspend fun createDictionary(dictionary: Dictionary):Boolean {
        dao.getDictionaryByName(dictionary.name)?.let {
            return false
        }
        dao.createDictionary(DictionaryMapper.mapToDatabaseModel(dictionary))
        return true
    }

    override fun editDictionary(dictionary: Dictionary) {
        TODO("Not yet implemented")
    }

    override fun deleteDictionary(dictionary: Dictionary) {
        TODO("Not yet implemented")
    }

    override suspend fun getDictionary(dictionaryId: Long): Dictionary {
        return DictionaryMapper.mapToDomainEntity(
            dao.getDictionaryById(dictionaryId)
        )
    }

    override fun getAllDictionaries(): LiveData<List<Dictionary>> {
        return dao.getAllDictionaries().map {
                list -> DictionaryMapper.mapListToDomainEntityList(list)
        }
    }

    override suspend fun getLastCreatedDictionary(): Dictionary? {
        return dao.getLastCreatedDictionary()?.let {
               DictionaryMapper.mapToDomainEntity(it)
            }
    }

    override suspend fun isAnyDictionaryExists(): Boolean {
        return dao.getLastCreatedDictionary() != null
    }
}