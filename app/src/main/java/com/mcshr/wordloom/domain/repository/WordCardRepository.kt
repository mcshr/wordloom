package com.mcshr.wordloom.domain.repository

import androidx.lifecycle.LiveData
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.entities.WordStatus

interface WordCardRepository {
    //word card
    suspend fun createWordCard(wordCard: WordCard):Long?
    suspend fun saveWordCardToDictionary(dictionaryId: Long, wordCardId: Long)
    suspend fun editWordCard(wordCard: WordCard)
    suspend fun editWordCardList(list: List<WordCard>)
    fun deleteWordCard(wordCard:WordCard)
    fun getWordCardById(wordCardId: Int):WordCard
    fun getWordCardListByDictId(dictionaryId:Long): LiveData<List<WordCard>>
    suspend fun getRepeatCardsCountFromSelectedDictionaries(currentTimeUnix: Long):Int
    suspend fun getNextRepeatTime(currentTimeUnix: Long, limit: Int): List<Long>?
    suspend fun getWordCardsForReview(currentTimeUnix: Long, limit:Int):List<WordCard>
    suspend fun getWordCardsByStatusFromSelectedDictionaries(wordStatus: WordStatus, limit:Int =0): List<WordCard>


}