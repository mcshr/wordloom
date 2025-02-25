package com.mcshr.wordloom.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.mcshr.wordloom.data.database.AppDatabase
import com.mcshr.wordloom.data.entities.CardDbModel
import com.mcshr.wordloom.data.entities.CardTranslationDbModel
import com.mcshr.wordloom.data.entities.TranslationDbModel
import com.mcshr.wordloom.data.entities.WordDbModel
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.repository.WordCardRepository

class WordCardRepositoryImpl(application: Application) : WordCardRepository {

    private val database = AppDatabase.getInstance(application)
    private val dao = database.wordCardDao()

    //TODO create mappers
    override suspend fun createWordCard(wordCard: WordCard):Boolean {
        val word = WordDbModel(
            id = 0,
            wordText = wordCard.wordText,
            languageId = 1, //TODO
            partOfSpeechId = 1
        )
        dao.getWordId(word.wordText, word.languageId, word.partOfSpeechId)?.let{
            return false
        }

        val wordId =  dao.createWord(word)

        val card = CardDbModel(
            id = 0,
            status = wordCard.status,
            reviewsCount = wordCard.reviewCount,
            nextRevDate = wordCard.nextReviewTime,
            imagePath = wordCard.imagePath,
            wordId = wordId
        )
        val cardId = dao.createCard(card)

        for (meaning in wordCard.wordTranslations) {
            val meaningDbModel = WordDbModel(
                id = 0,
                wordText = meaning,
                languageId = 1, //TODO
                partOfSpeechId = 1
            )

            val translationId = dao.getWordId(
                meaningDbModel.wordText,
                meaningDbModel.languageId,
                meaningDbModel.partOfSpeechId
            )?: dao.createWord(meaningDbModel)

            val translation = dao.createTranslation(
                TranslationDbModel(
                    id = 0,
                    wordIdOriginal = wordId,
                    wordIdTranslation = translationId
                )
            )
            dao.createCardTranslation(
                CardTranslationDbModel(
                    cardId = cardId,
                    translationId = translation
                )
            )
        }

        return true
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