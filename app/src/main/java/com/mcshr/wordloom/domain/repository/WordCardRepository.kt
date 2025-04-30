package com.mcshr.wordloom.domain.repository

import androidx.lifecycle.LiveData
import com.mcshr.wordloom.domain.entities.WordCard

interface WordCardRepository {
    //word card
    suspend fun createWordCard(wordCard: WordCard):Long?
    suspend fun saveWordCardToDictionary(dictionaryId: Long, wordCardId: Long)
    suspend fun editWordCard(wordCard: WordCard)
    fun deleteWordCard(wordCard:WordCard)
    fun getWordCardById(wordCardId: Int):WordCard
    fun getWordCardListByDictId(dictionaryId:Long): LiveData<List<WordCard>>
    fun getWordsForReview(currentTime: Long):List<WordCard>
    fun getReadyToRepeatCardsCountFromSelectedDictionaries(currentTimeUnix: Long):LiveData<Int>

}