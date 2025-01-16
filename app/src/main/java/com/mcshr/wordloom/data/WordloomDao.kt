package com.mcshr.wordloom.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mcshr.wordloom.data.entities.CardDbModel
import com.mcshr.wordloom.data.entities.CardTranslationDbModel
import com.mcshr.wordloom.data.entities.DictionaryDbModel
import com.mcshr.wordloom.data.entities.TranslationDbModel
import com.mcshr.wordloom.data.entities.WordDbModel
import com.mcshr.wordloom.data.entities.tuples.SelectedDictionaryCardView
import com.mcshr.wordloom.data.entities.tuples.WordCardRelation
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.entities.WordStatus

@Dao
interface WordloomDao {
    @Insert
    suspend fun createDictionary(dictionary:DictionaryDbModel)

    @Update
    fun editDictionary(dictionary: DictionaryDbModel)

    @Query("SELECT * FROM dictionary WHERE is_selected == 1")
    fun getSelectedDictionaries():LiveData<List<DictionaryDbModel>>

    @Query("SELECT * FROM dictionary")
    fun getAllDictionaries():LiveData<List<DictionaryDbModel>>

    @Delete
    fun deleteDictionary(dictionary: DictionaryDbModel)

    @Query("SELECT * FROM selected_dictionary_with_cards WHERE status == :wordStatus")
    fun getWordCardsListWithStatus(wordStatus: WordStatus):LiveData<List<SelectedDictionaryCardView>>

    @Transaction
    @Query("SELECT * FROM card WHERE id == :cardId")
    fun getWordCardByCardId(cardId:Int):LiveData<WordCardRelation>

    @Insert
    fun createCard(cardDbModel: CardDbModel): Long

    @Insert
    fun createWord(wordDbModel: WordDbModel): Long

    @Insert
    fun createTranslation(translationDbModel: TranslationDbModel): Long

    @Insert
    fun createCardTranslation(cardTranslationDbModel: CardTranslationDbModel)


    @Transaction
    fun insertWordCard(wordCard: WordCard){

        val word = WordDbModel(
            id = 0,
            wordText = wordCard.wordText,
            languageId = 0, //TODO
            partOfSpeechId = null
        )
        val wordId = createWord(word)

        val card = CardDbModel(
            id = 0,
            status = wordCard.status,
            reviewsCount = wordCard.reviewCount,
            nextRevDate = wordCard.nextReviewTime,
            imagePath = wordCard.imagePath,
            wordId = wordId
        )
        val cardId = createCard(card)

        for(meaning in wordCard.wordTranslations) {
            val meaningDbModel = WordDbModel(
                id = 0,
                wordText = meaning,
                languageId = 0, //TODO
                partOfSpeechId = null
            )
            val translationId = createWord(meaningDbModel)


            val translation = createTranslation(TranslationDbModel(
                id = 0,
                wordIdOriginal = wordId,
                wordIdTranslation = translationId
            ))
            createCardTranslation(CardTranslationDbModel(
                cardId = cardId,
                translationId = translation
            ))
        }
    }

}