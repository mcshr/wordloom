package com.mcshr.wordloom.domain.repository

import androidx.lifecycle.LiveData
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.entities.WordStatus

interface WordCardRepository {
    //word card
    suspend fun createWordCard(wordCard: WordCard):Long?
    suspend fun getWordCardIfTranslationsExists(wordCard: WordCard): WordCard?
    suspend fun getWordCardById(wordCardId: Long): WordCard
    suspend fun saveWordCardToDictionary(dictionaryId: Long, wordCardId: Long)
    suspend fun editWordCard(newWordCard: WordCard)
    suspend fun updateInfoWordCard(wordCard: WordCard)
    suspend fun updateInfoWordCardList(list: List<WordCard>)
    suspend fun removeWordCardFromDictionary(wordCardId: Long, dictionaryId: Long)
    suspend fun getDictionaryCountForWordCard(wordCardId: Long):Int
    suspend fun deleteWordCard(wordCard:WordCard)
    fun getWordCardListByDictId(dictionaryId:Long): LiveData<List<WordCard>>
    suspend fun getRepeatCardsCountFromSelectedDictionaries(currentTimeUnix: Long):Int
    suspend fun getNextRepeatTime(currentTimeUnix: Long, limit: Int): List<Long>?
    suspend fun getWordCardsForReview(currentTimeUnix: Long, limit:Int):List<WordCard>
    suspend fun getWordCardsByStatusFromSelectedDictionaries(wordStatus: WordStatus, limit:Int =0): List<WordCard>


}