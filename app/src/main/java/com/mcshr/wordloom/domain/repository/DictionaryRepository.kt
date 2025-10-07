package com.mcshr.wordloom.domain.repository

import androidx.lifecycle.LiveData
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.entities.DictionaryWithStats

interface DictionaryRepository {
    suspend fun createDictionary(dictionary: Dictionary):Boolean
    suspend fun editDictionary(dictionary: Dictionary)
    suspend fun deleteDictionary(dictionary: Dictionary)
    suspend fun getDictionary(dictionaryId:Long): Dictionary
    fun getAllDictionaries(): LiveData<List<Dictionary>>
    fun getAllDictionariesWithStats() : LiveData<List<DictionaryWithStats>>
    fun getSelectedDictionariesWithStats() : LiveData<List<DictionaryWithStats>>
    suspend fun getLastCreatedDictionary(): Dictionary?
    suspend fun isAnyDictionaryExists(): Boolean
}