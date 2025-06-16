package com.mcshr.wordloom.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mcshr.wordloom.data.entities.CardDbModel
import com.mcshr.wordloom.data.entities.CardTranslationDbModel
import com.mcshr.wordloom.data.entities.DictionaryCardDbModel
import com.mcshr.wordloom.data.entities.TranslationDbModel
import com.mcshr.wordloom.data.entities.WordDbModel
import com.mcshr.wordloom.data.entities.tuples.DictionaryWithCardsRelation
import com.mcshr.wordloom.data.entities.tuples.SelectedDictionaryCardView
import com.mcshr.wordloom.data.entities.tuples.WordCardRelation
import com.mcshr.wordloom.domain.entities.WordStatus

@Dao
interface WordCardDao {

    @Query("SELECT * FROM selected_dictionary_with_cards WHERE status == :wordStatus")
    fun getWordCardsListWithStatus(wordStatus: WordStatus): LiveData<List<SelectedDictionaryCardView>>

    @Transaction
    @Query("SELECT * FROM card WHERE id == :cardId")
    fun getWordCardByCardId(cardId: Long): LiveData<WordCardRelation>

    @Query(
        "SELECT id FROM word WHERE word_text == :wordText " +
                "AND part_of_speech_id ==:partOfSpeechId " +
                "AND language_id =:languageId LIMIT 1"
    )
    suspend fun getWordId(wordText: String, languageId: Long, partOfSpeechId: Int?): Long?

    @Transaction
    @Query("SELECT * FROM dictionary WHERE id ==:dictionaryId LIMIT 1")
    fun getWordCardsFromDictionary(dictionaryId: Long): LiveData<DictionaryWithCardsRelation>

    @Query(
        "SELECT COUNT(card.id) FROM card " +
                "INNER JOIN dictionary_card dc ON card.id = dc.card_id " +
                "INNER JOIN dictionary ON dc.dictionary_id = dictionary.id " +
                "WHERE dictionary.is_selected = 1 " +
                "AND next_rev_date <= :currentTime " +
                "AND status = :statusLearning"
    )
    suspend fun getCardRepeatCountFromSelectedDictionaries(
        currentTime: Long,
        statusLearning: WordStatus = WordStatus.LEARNING
    ): Int

    @Query("SELECT card.next_rev_date FROM card " +
            "INNER JOIN dictionary_card dc ON card.id = dc.card_id " +
            "INNER JOIN dictionary ON dc.dictionary_id = dictionary.id " +
            "WHERE dictionary.is_selected = 1 " +
            "AND next_rev_date > :currentTime " +
            "AND status = :statusLearning " +
            "ORDER BY card.next_rev_date " +
            "LIMIT :limit")
    suspend fun getNextRepeatTime(
        currentTime: Long,
        limit: Int,
        statusLearning: WordStatus = WordStatus.LEARNING
    ):List<Long>?

    @Transaction
    @Query(
        "SELECT * FROM card " +
                "INNER JOIN dictionary_card dc ON card.id = dc.card_id " +
                "INNER JOIN dictionary ON dc.dictionary_id = dictionary.id " +
                "WHERE dictionary.is_selected = 1 AND card.status  = :wordStatus"
    )
    fun getWordCardsByStatusFromSelectedDicts(
        wordStatus: WordStatus
    ): List<WordCardRelation>

    @Transaction
    @Query(
        "SELECT * FROM card " +
                "INNER JOIN dictionary_card dc ON card.id = dc.card_id " +
                "INNER JOIN dictionary ON dc.dictionary_id = dictionary.id " +
                "WHERE dictionary.is_selected = 1 AND card.status  = :wordStatus " +
                "LIMIT :limit"
    )
    fun getWordCardsByStatusFromSelectedDicts(
        wordStatus: WordStatus,
        limit: Int
    ): List<WordCardRelation>


    @Transaction
    @Query(
        "WITH learningCards AS " +
                "( SELECT * FROM card " +
                "INNER JOIN dictionary_card dc ON card.id = dc.card_id " +
                "INNER JOIN dictionary dict ON dc.dictionary_id = dict.id " +
                "WHERE dict.is_selected = 1 " +
                "AND card.status = :learningStatus AND card.next_rev_date <= :currentTime " +
                "LIMIT :limit ), " + //adding two more limits for optimisation
                "readyToLearnCards AS (SELECT * FROM card " +
                "INNER JOIN dictionary_card dc ON card.id = dc.card_id " +
                "INNER JOIN dictionary dict ON dc.dictionary_id = dict.id " +
                "WHERE dict.is_selected = 1 " +
                "AND card.status = :readyToLearnStatus " +
                "LIMIT :limit ) " +
                "SELECT * FROM learningCards " +
                "UNION ALL " +
                "SELECT * FROM readyToLearnCards " +
                "LIMIT :limit"
    )
    suspend fun getLearningSet(
        currentTime: Long,
        learningStatus: WordStatus = WordStatus.LEARNING,
        readyToLearnStatus: WordStatus = WordStatus.READY_TO_LEARN,
        limit: Int
    ): List<WordCardRelation>

    @Transaction
    @Query(
        "SELECT * FROM card " +
                "INNER JOIN dictionary_card dc ON card.id = dc.card_id " +
                "INNER JOIN dictionary dict ON dc.dictionary_id = dict.id " +
                "WHERE dict.is_selected = 1 " +
                "AND card.status = :learningStatus AND card.next_rev_date <= :currentTime " +
                "LIMIT :limit"
    )
    suspend fun getWordCardsForReview(
        currentTime: Long,
        limit: Int,
        learningStatus: WordStatus = WordStatus.LEARNING,
    ): List<WordCardRelation>

    @Query("SELECT * FROM card " +
            "INNER JOIN word ON card.word_id = word.id " +
            "WHERE word_id = :wordId")
    suspend fun getCardByWordId(wordId:Long): CardDbModel?

    @Insert
    suspend fun createCard(cardDbModel: CardDbModel): Long

    @Insert
    suspend fun createWord(wordDbModel: WordDbModel): Long

    @Insert
    suspend fun createTranslation(translationDbModel: TranslationDbModel): Long

    @Insert
    suspend fun createCardTranslation(cardTranslationDbModel: CardTranslationDbModel)

    @Insert
    suspend fun addCardToDictionary(dictionaryCard: DictionaryCardDbModel)

    @Update
    suspend fun editCard(cardDbModel: CardDbModel)

    @Update
    suspend fun editCardsList(list: List<CardDbModel>)


    @Delete
    suspend fun deleteCard(cardDbModel: CardDbModel)


}