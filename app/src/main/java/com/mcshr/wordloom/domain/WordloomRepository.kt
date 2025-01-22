package com.mcshr.wordloom.domain

import androidx.lifecycle.LiveData
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.entities.WordCard

interface WordloomRepository {
    //word card
    suspend fun createWordCard(wordCard: WordCard)
    fun editWordCard(wordCard: WordCard)
    fun deleteWordCard(wordCard:WordCard)
    fun getWordCardById(wordCardId: Int):WordCard
    fun getWordCardList(): LiveData<List<WordCard>>
    fun getWordsForReview(currentTime: Long):List<WordCard>


    //dictionary
    suspend fun createDictionary(dictionary: Dictionary)
    fun editDictionary(dictionary: Dictionary)
    fun deleteDictionary(dictionary:Dictionary)
    suspend fun getDictionary(dictionaryId:Long):Dictionary
    fun getAllDictionaries():LiveData<List<Dictionary>>
}