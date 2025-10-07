package com.mcshr.wordloom.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.withTransaction
import com.mcshr.wordloom.data.database.AppDatabase
import com.mcshr.wordloom.data.mappers.toDBModel
import com.mcshr.wordloom.data.mappers.toDictionaryListDomain
import com.mcshr.wordloom.data.mappers.toDictionaryWithStatsListDomain
import com.mcshr.wordloom.data.mappers.toDomainEntity
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.entities.DictionaryWithStats
import com.mcshr.wordloom.domain.repository.DictionaryRepository
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val database: AppDatabase
) : DictionaryRepository {
    private val dictDao = database.dictionaryDao()
    private val cardDao = database.wordCardDao()

    override suspend fun createDictionary(dictionary: Dictionary): Boolean {
        dictDao.getDictionaryByName(dictionary.name)?.let {
            return false
        }
        dictDao.createDictionary(dictionary.toDBModel())

        return true
    }

    override suspend fun editDictionary(dictionary: Dictionary) {
        dictDao.editDictionary(dictionary.toDBModel())
    }

    override suspend fun deleteDictionary(dictionary: Dictionary) {
        database.withTransaction {
            dictDao.deleteDictionary(dictionary.toDBModel())
            cardDao.deleteUnusedCards()
            cardDao.deleteUnusedTranslations()
            cardDao.deleteUnusedWords()
        }

    }

    override suspend fun getDictionary(dictionaryId: Long): Dictionary {
        return dictDao.getDictionaryById(dictionaryId).toDomainEntity()
    }

    override fun getAllDictionaries(): LiveData<List<Dictionary>> {
        return dictDao.getAllDictionaries().map { list ->
            list.toDictionaryListDomain()
        }
    }

    override fun getAllDictionariesWithStats(): LiveData<List<DictionaryWithStats>> {
        return dictDao.getAllDictWithStats().map { it.toDictionaryWithStatsListDomain() }
    }

    override fun getSelectedDictionariesWithStats(): LiveData<List<DictionaryWithStats>> {
        return dictDao.getSelectedDictWithStats().map { it.toDictionaryWithStatsListDomain() }
    }

    override suspend fun getLastCreatedDictionary(): Dictionary? {
        return dictDao.getLastCreatedDictionary()?.toDomainEntity()
    }

    override suspend fun isAnyDictionaryExists(): Boolean {
        return dictDao.getLastCreatedDictionary() != null
    }
}