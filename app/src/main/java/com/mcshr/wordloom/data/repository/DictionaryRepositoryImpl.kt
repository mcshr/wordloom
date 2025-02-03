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
    private val dictMapper = DictionaryMapper()

    override suspend fun createDictionary(dictionary: Dictionary) {
        dao.createDictionary(dictMapper.mapToDatabaseModel(dictionary))
    }

    override fun editDictionary(dictionary: Dictionary) {
        TODO("Not yet implemented")
    }

    override fun deleteDictionary(dictionary: Dictionary) {
        TODO("Not yet implemented")
    }

    override suspend fun getDictionary(dictionaryId: Long): Dictionary {
        return dictMapper.mapToDomainEntity(
            dao.getDictionaryById(dictionaryId)
        )
    }

    override fun getAllDictionaries(): LiveData<List<Dictionary>> {
        return dao.getAllDictionaries().map {
                list -> dictMapper.mapListToDomainEntityList(list)
        }
    }

    override suspend fun getLastCreatedDictionary(): Dictionary? {
        return dao.getLastCreatedDictionary()?.let {
               dictMapper.mapToDomainEntity(it)
            }
    }
}