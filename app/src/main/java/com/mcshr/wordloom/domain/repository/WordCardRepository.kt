package com.mcshr.wordloom.domain.repository

import androidx.lifecycle.LiveData
import com.mcshr.wordloom.domain.entities.WordCard

interface WordCardRepository {
    //word card
    suspend fun createWordCard(wordCard: WordCard)
    fun editWordCard(wordCard: WordCard)
    fun deleteWordCard(wordCard:WordCard)
    fun getWordCardById(wordCardId: Int):WordCard
    fun getWordCardList(): LiveData<List<WordCard>>
    fun getWordsForReview(currentTime: Long):List<WordCard>

}