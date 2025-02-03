package com.mcshr.wordloom.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.mcshr.wordloom.data.database.AppDatabase
import com.mcshr.wordloom.domain.repository.WordCardRepository
import com.mcshr.wordloom.domain.entities.WordCard

class WordCardRepositoryImpl(application: Application) : WordCardRepository {

    private val database = AppDatabase.getInstance(application)
    private val dao = database.wordCardDao()

    override suspend fun createWordCard(wordCard: WordCard) {
        dao.insertWordCard(wordCard)
    }

    override fun editWordCard(wordCard: WordCard) {
        TODO("Not yet implemented")
    }

    override fun deleteWordCard(wordCard: WordCard) {
        TODO("Not yet implemented")
    }

    override fun getWordCardById(wordCardId: Int): WordCard {
        TODO("Not yet implemented")
    }

    override fun getWordCardList(): LiveData<List<WordCard>> {
        TODO("Not yet implemented")
    }

    override fun getWordsForReview(currentTime: Long): List<WordCard> {
        TODO("Not yet implemented")
    }
}