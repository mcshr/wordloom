package com.mcshr.wordloom.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mcshr.wordloom.data.entities.DictionaryDbModel
import com.mcshr.wordloom.data.entities.tuples.WordCardRelation
import com.mcshr.wordloom.domain.entities.WordStatus

@Dao
interface WordloomDao {
    @Insert
    fun addDictionary(dictionary:DictionaryDbModel)

    @Update
    fun editDictionary(dictionary: DictionaryDbModel)

    @Query("SELECT * FROM dictionary WHERE is_selected == 1")
    fun getSelectedDictionaries():LiveData<List<DictionaryDbModel>>

    @Query("SELECT * FROM dictionary")
    fun getAllDictionaries():LiveData<List<DictionaryDbModel>>

    @Delete
    fun deleteDictionary(dictionary: DictionaryDbModel)

    @Query("SELECT * FROM dictionary_with_cards WHERE status == :wordStatus")
    fun getWordCardsListWithStatus(wordStatus: WordStatus)

    @Transaction
    @Query("SELECT * FROM card WHERE id == :cardId")
    fun getWordCardByCardId(cardId:Int):LiveData<WordCardRelation>
}