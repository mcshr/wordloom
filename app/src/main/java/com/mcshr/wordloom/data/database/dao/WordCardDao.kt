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
import com.mcshr.wordloom.data.entities.UsageExampleDbModel
import com.mcshr.wordloom.data.entities.WordDbModel
import com.mcshr.wordloom.data.entities.tuples.DictionaryWithCardsRelation
import com.mcshr.wordloom.data.entities.tuples.WordCardRelation
import com.mcshr.wordloom.domain.entities.WordStatus

@Dao
interface WordCardDao {

//    @Query("SELECT * FROM selected_dictionary_with_cards WHERE status == :wordStatus")
//    fun getWordCardsListWithStatus(wordStatus: WordStatus): LiveData<List<SelectedDictionaryCardView>>

//    @Transaction
//    @Query("SELECT * FROM card WHERE id == :cardId")
//    fun getWordCardLiveDataByCardId(cardId: Long): LiveData<WordCardRelation>

    @Query("SELECT * FROM card WHERE id == :cardId")
    suspend fun getCardById(cardId: Long): CardDbModel

    @Transaction
    @Query("SELECT * FROM card WHERE id == :cardId")
    suspend fun getWordCardByCardId(cardId: Long): WordCardRelation

    @Query(
        "SELECT id FROM word WHERE word_text == :wordText " +
//                "COLLATE NOCASE " +
                "AND part_of_speech_code ==:partOfSpeechCode " +
                "AND language_id =:languageId LIMIT 1"
    )
    suspend fun getWordId(wordText: String, languageId: Long, partOfSpeechCode:String): Long?

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

    @Query(
        "SELECT card.next_rev_date FROM card " +
                "INNER JOIN dictionary_card dc ON card.id = dc.card_id " +
                "INNER JOIN dictionary ON dc.dictionary_id = dictionary.id " +
                "WHERE dictionary.is_selected = 1 " +
                "AND next_rev_date > :currentTime " +
                "AND status = :statusLearning " +
                "ORDER BY card.next_rev_date " +
                "LIMIT :limit"
    )
    suspend fun getNextRepeatTime(
        currentTime: Long,
        limit: Int,
        statusLearning: WordStatus = WordStatus.LEARNING
    ): List<Long>?

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

    @Insert
    suspend fun createCard(cardDbModel: CardDbModel): Long

    @Insert
    suspend fun createWord(wordDbModel: WordDbModel): Long

    @Insert
    suspend fun createTranslation(translationDbModel: TranslationDbModel): Long

    @Insert
    suspend fun createCardTranslation(cardTranslationDbModel: CardTranslationDbModel)

    @Insert
    suspend fun createUsageExample(usageExampleDbModel: UsageExampleDbModel)

    @Insert
    suspend fun addCardToDictionary(dictionaryCard: DictionaryCardDbModel)

    @Update
    suspend fun editCard(cardDbModel: CardDbModel)

    @Update
    suspend fun editCardsList(list: List<CardDbModel>)

    @Query(
        "SELECT COUNT(*) FROM dictionary d " +
                "LEFT JOIN dictionary_card dc ON d.id = dc.dictionary_id " +
                "LEFT JOIN card c ON dc.card_id = c.id " +
                "WHERE c.id = :cardId"
    )
    suspend fun getDictionaryCountForCard(cardId: Long): Int

    @Query(
        "SELECT * FROM card c " +
                "INNER JOIN card_translation ct ON c.id = ct.card_id " +
                "INNER JOIN translation t ON ct.translation_id = t.id " +
                "WHERE t.word_id_original = :wordIdOriginal " +
                "AND t.word_id_translation = :wordIdTranslation " +
                "LIMIT 1"
    )
    suspend fun getWordCardByTranslation(
        wordIdOriginal: Long,
        wordIdTranslation: Long
    ): WordCardRelation?

    @Delete
    suspend fun removeCardFromDictionary(dictionaryCard: DictionaryCardDbModel)

    @Query(
        "DELETE FROM card " +
                "WHERE NOT EXISTS " +
                "(SELECT 1 FROM dictionary_card dc " +
                "WHERE dc.card_id = card.id)"
    )
    suspend fun deleteUnusedCards()

    @Delete
    suspend fun deleteCard(cardDbModel: CardDbModel)

    @Delete
    suspend fun deleteTranslation(translation: TranslationDbModel)

    @Query(
        "DELETE FROM translation " +
                "WHERE NOT EXISTS " +
                "( SELECT 1 FROM card_translation ct " +
                "WHERE ct.translation_id = translation.id )"
    )
    suspend fun deleteUnusedTranslations()

    @Delete
    suspend fun deleteWord(wordDbModel: WordDbModel)

    @Query(
        "DELETE FROM word WHERE " +
                "NOT EXISTS (SELECT 1 FROM translation t WHERE t.word_id_original = word.id) " +
                "AND NOT EXISTS (SELECT 1 FROM translation t WHERE t.word_id_translation = word.id) "
    )
    suspend fun deleteUnusedWords()
}