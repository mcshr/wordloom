package com.mcshr.wordloom.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mcshr.wordloom.data.database.AppDatabase
import com.mcshr.wordloom.data.entities.CardDbModel
import com.mcshr.wordloom.data.entities.CardTranslationDbModel
import com.mcshr.wordloom.data.entities.DictionaryCardDbModel
import com.mcshr.wordloom.data.entities.TranslationDbModel
import com.mcshr.wordloom.data.entities.WordDbModel
import com.mcshr.wordloom.data.entities.mappers.WordCardMapper
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.repository.WordCardRepository

class WordCardRepositoryImpl(application: Application) : WordCardRepository {

    private val database = AppDatabase.getInstance(application)
    private val dao = database.wordCardDao()
    private val wordCardMapper = WordCardMapper()

    //TODO create mappers
    override suspend fun createWordCard(wordCard: WordCard):Long? {
        val word = WordDbModel(
            id = 0,
            wordText = wordCard.wordText,
            languageId = 1, //TODO
            partOfSpeechId = 1
        )
        dao.getWordId(word.wordText, word.languageId, word.partOfSpeechId)?.let{
            return null
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

        return cardId
    }

    override suspend fun saveWordCardToDictionary(dictionaryId: Long, wordCardId: Long){
        dao.addCardToDictionary(
            DictionaryCardDbModel(
                dictionaryId = dictionaryId,
                cardId = wordCardId
            )
        )
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

    override fun getWordCardListByDictId(dictionaryId: Long): LiveData<List<WordCard>> {
       val list = dao.getWordCardsFromDictionary(dictionaryId)
        Log.d("siaposjdaklsjdla",list.value.toString())
        return dao.getWordCardsFromDictionary(dictionaryId).map {
            wordCardMapper.mapListDBModelToListDomainEntity(it)
        }
    }

    override fun getWordsForReview(currentTime: Long): List<WordCard> {
        TODO("Not yet implemented")
    }
}