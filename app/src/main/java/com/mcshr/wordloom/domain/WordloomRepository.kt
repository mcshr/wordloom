package com.mcshr.wordloom.domain

import androidx.lifecycle.LiveData
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.entities.WordCard
import java.time.LocalDateTime

interface WordloomRepository {
    //word card
    fun addWordCard(wordCard: WordCard)
    fun editWordCard(wordCard: WordCard)
    fun deleteWordCard(wordCard:WordCard)
    fun getWordCardById(wordCardId: Int):WordCard
    fun getWordCardList(): LiveData<List<WordCard>>
    fun getWordsForReview(currentTime: LocalDateTime):List<WordCard>


    //dictionary
    fun addDictionary(dictionary: Dictionary)
    fun editDictionary(dictionary: Dictionary)
    fun deleteDictionary(dictionary:Dictionary)
}