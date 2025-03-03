package com.mcshr.wordloom.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mcshr.wordloom.data.database.AppDatabase
import com.mcshr.wordloom.data.entities.CardTranslationDbModel
import com.mcshr.wordloom.data.entities.DictionaryCardDbModel
import com.mcshr.wordloom.data.entities.TranslationDbModel
import com.mcshr.wordloom.data.entities.mappers.WordCardMapper
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.repository.WordCardRepository

class WordCardRepositoryImpl(application: Application) : WordCardRepository {

    private val database = AppDatabase.getInstance(application)
    private val dao = database.wordCardDao()

    override suspend fun createWordCard(wordCard: WordCard):Long? {
        val word = WordCardMapper.mapWordCardToWord(wordCard)
        dao.getWordId(word.wordText, word.languageId, word.partOfSpeechId)?.let{
            return null
        }

        val wordId =  dao.createWord(word)

        val card = WordCardMapper.mapWordCardToCard(wordCard, wordId)
        val cardId = dao.createCard(card)

        val meaningList = WordCardMapper.mapWordCardToMeaningList(wordCard)
        for (meaning in  meaningList) {

            val translationId = dao.getWordId(
                meaning.wordText,
                meaning.languageId,
                meaning.partOfSpeechId
            )?: dao.createWord(meaning)

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
        return dao.getWordCardsFromDictionary(dictionaryId).map {
            WordCardMapper.mapListDBModelToListDomainEntity(it)
        }
    }

    override fun getWordsForReview(currentTime: Long): List<WordCard> {
        TODO("Not yet implemented")
    }
}