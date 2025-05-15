package com.mcshr.wordloom.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mcshr.wordloom.data.database.AppDatabase
import com.mcshr.wordloom.data.entities.CardTranslationDbModel
import com.mcshr.wordloom.data.entities.DictionaryCardDbModel
import com.mcshr.wordloom.data.entities.TranslationDbModel
import com.mcshr.wordloom.data.entities.mappers.toCardDomain
import com.mcshr.wordloom.data.entities.mappers.toDomainEntity
import com.mcshr.wordloom.data.entities.mappers.toTranslationsList
import com.mcshr.wordloom.data.entities.mappers.toWordCardListDomain
import com.mcshr.wordloom.data.entities.mappers.toWordDomain
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.entities.WordStatus
import com.mcshr.wordloom.domain.repository.WordCardRepository
import javax.inject.Inject

class WordCardRepositoryImpl @Inject constructor(
    database: AppDatabase
) : WordCardRepository {
    private val dao = database.wordCardDao()

    override suspend fun createWordCard(wordCard: WordCard): Long? {
        val word = wordCard.toWordDomain()
        dao.getWordId(word.wordText, word.languageId, word.partOfSpeechId)?.let {
            return null
        }

        val wordId = dao.createWord(word)

        val card = wordCard.toCardDomain(wordId)
        val cardId = dao.createCard(card)

        val meaningList = wordCard.toTranslationsList()
        for (meaning in meaningList) {
            val translationId = dao.getWordId(
                meaning.wordText,
                meaning.languageId,
                meaning.partOfSpeechId
            ) ?: dao.createWord(meaning)

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

    override suspend fun saveWordCardToDictionary(dictionaryId: Long, wordCardId: Long) {
        dao.addCardToDictionary(
            DictionaryCardDbModel(
                dictionaryId = dictionaryId,
                cardId = wordCardId
            )
        )
    }

    override suspend fun editWordCard(wordCard: WordCard) {
        dao.editCard(wordCard.toCardDomain(wordCard.id))
        //TODO("Not yet implemented")
    }

    override suspend fun editWordCardList(list: List<WordCard>) {
        dao.editCardsList(
            list.map { wordCard ->
                wordCard.toCardDomain(wordCard.id)
            }
        )
    }

    override fun deleteWordCard(wordCard: WordCard) {
        TODO("Not yet implemented")
    }

    override fun getWordCardById(wordCardId: Int): WordCard {
        TODO("Not yet implemented")
    }

    override fun getWordCardListByDictId(dictionaryId: Long): LiveData<List<WordCard>> {
        return dao.getWordCardsFromDictionary(dictionaryId).map {
            it.toWordCardListDomain()
        }
    }

    override fun getReadyToRepeatCardsCountFromSelectedDictionaries(currentTimeUnix: Long): LiveData<Int> {
        return dao.getCardRepeatCountFromSelectedDictionaries(currentTimeUnix)
    }

    override suspend fun getWordCardsForReview(
        currentTime: Long,
        limit: Int
    ): List<WordCard> {
        return dao.getWordCardsForReview(currentTime, limit).map{it.toDomainEntity()}
    }

    override suspend fun getWordCardsByStatusFromSelectedDictionaries(
        wordStatus: WordStatus,
        limit: Int
    ): List<WordCard> {
        return if(limit>0) {
            dao.getWordCardsByStatusFromSelectedDicts(wordStatus, limit).map { it.toDomainEntity() }
        } else {
            dao.getWordCardsByStatusFromSelectedDicts(wordStatus).map { it.toDomainEntity() }
        }
    }
}