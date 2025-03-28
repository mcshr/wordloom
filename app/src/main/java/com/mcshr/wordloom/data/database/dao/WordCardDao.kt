package com.mcshr.wordloom.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
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

    @Query("SELECT id FROM word WHERE word_text == :wordText " +
            "AND part_of_speech_id ==:partOfSpeechId " +
            "AND language_id =:languageId LIMIT 1")
    suspend fun getWordId(wordText: String, languageId: Long, partOfSpeechId: Int?): Long?

    @Transaction
    @Query("SELECT * FROM dictionary WHERE id ==:dictionaryId LIMIT 1")
    fun getWordCardsFromDictionary(dictionaryId: Long):LiveData<DictionaryWithCardsRelation>

    @Query("SELECT COUNT(card.id) FROM card " +
            "INNER JOIN dictionary_card dc ON card.id = dc.card_id " +
            "INNER JOIN dictionary ON dc.dictionary_id == dictionary.id " +
            "WHERE dictionary.is_selected = 1 AND next_rev_date <= :currentTime")
    fun getCardRepeatCountFromSelectedDictionaries(
        currentTime: Long
    ):LiveData<Int>

    @Insert
    suspend fun createCard(cardDbModel: CardDbModel): Long

    @Insert
    suspend fun createWord(wordDbModel: WordDbModel): Long

    @Insert
    suspend fun createTranslation(translationDbModel: TranslationDbModel): Long

    @Insert
    suspend fun createCardTranslation(cardTranslationDbModel: CardTranslationDbModel)

    @Insert
    suspend fun addCardToDictionary(dictionaryCard:DictionaryCardDbModel)

}