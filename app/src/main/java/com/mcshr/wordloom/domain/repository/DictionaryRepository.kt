package com.mcshr.wordloom.domain.repository

import androidx.lifecycle.LiveData
import com.mcshr.wordloom.domain.entities.Dictionary

interface DictionaryRepository {
    suspend fun createDictionary(dictionary: Dictionary)
    fun editDictionary(dictionary: Dictionary)
    fun deleteDictionary(dictionary: Dictionary)
    suspend fun getDictionary(dictionaryId:Long): Dictionary
    fun getAllDictionaries(): LiveData<List<Dictionary>>
    suspend fun getLastCreatedDictionary(): Dictionary?
    suspend fun isAnyDictionaryExists(): Boolean
}