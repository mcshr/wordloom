package com.mcshr.wordloom.data.repository

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
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    database: AppDatabase
) : DictionaryRepository {
    private val dao = database.dictionaryDao()

    override suspend fun createDictionary(dictionary: Dictionary): Boolean {
        dao.getDictionaryByName(dictionary.name)?.let {
            return false
        }
        dao.createDictionary(dictionary.toDBModel())

        return true
    }

    override suspend fun editDictionary(dictionary: Dictionary) {
        dao.editDictionary(dictionary.toDBModel())
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

    override fun getSelectedDictionariesWithStats(): LiveData<List<DictionaryWithStats>> {
        return dao.getSelectedDictWithStats().map { it.toDictionaryWithStatsListDomain() }
    }

    override suspend fun getLastCreatedDictionary(): Dictionary? {
        return dao.getLastCreatedDictionary()?.toDomainEntity()
    }

    override suspend fun isAnyDictionaryExists(): Boolean {
        return dao.getLastCreatedDictionary() != null
    }
}