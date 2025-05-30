package com.mcshr.wordloom.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mcshr.wordloom.data.entities.DictionaryDbModel
import com.mcshr.wordloom.data.entities.tuples.DictionaryRelation
import com.mcshr.wordloom.data.entities.tuples.DictionaryWithStatsTuple
import com.mcshr.wordloom.domain.entities.WordStatus

@Dao
interface DictionaryDao {
    @Insert
    suspend fun createDictionary(dictionary: DictionaryDbModel)

    @Update
    suspend fun editDictionary(dictionary: DictionaryDbModel)

    @Transaction
    @Query("SELECT * FROM dictionary")
    fun getAllDictionaries(): LiveData<List<DictionaryRelation>>

    @Transaction
    @Query(
        "SELECT dictionary.* , " +
                "COUNT(card.id ) AS total, " +
                "COUNT(CASE WHEN card.status = :statusUnknown THEN 1 END) AS unknown, " +
                "COUNT(CASE WHEN card.status = :statusKnown THEN 1 END) AS known, " +
                "COUNT(CASE WHEN card.status = :statusReadyToLearn THEN 1 END) AS readyToLearn, " +
                "COUNT(CASE WHEN card.status = :statusLearning THEN 1 END) AS learning, " +
                "COUNT(CASE WHEN card.status = :statusLearned THEN 1 END) AS learned " +
                "FROM dictionary " +
                "LEFT JOIN dictionary_card dc ON dictionary.id = dc.dictionary_id " +
                "LEFT JOIN card ON dc.card_id = card.id " +
                "GROUP BY dictionary.id"
    )
    fun getAllDictWithStats(
        statusUnknown: WordStatus = WordStatus.UNKNOWN,
        statusKnown: WordStatus = WordStatus.KNOWN,
        statusReadyToLearn: WordStatus = WordStatus.READY_TO_LEARN,
        statusLearning: WordStatus = WordStatus.LEARNING,
        statusLearned: WordStatus = WordStatus.LEARNED
    ): LiveData<List<DictionaryWithStatsTuple>>

    @Transaction
    @Query(
        "SELECT dictionary.* , " +
                "COUNT(card.id ) AS total, " +
                "COUNT(CASE WHEN card.status = :statusUnknown THEN 1 END) AS unknown, " +
                "COUNT(CASE WHEN card.status = :statusKnown THEN 1 END) AS known, " +
                "COUNT(CASE WHEN card.status = :statusReadyToLearn THEN 1 END) AS readyToLearn, " +
                "COUNT(CASE WHEN card.status = :statusLearning THEN 1 END) AS learning, " +
                "COUNT(CASE WHEN card.status = :statusLearned THEN 1 END) AS learned " +
                "FROM dictionary " +
                "LEFT JOIN dictionary_card dc ON dictionary.id = dc.dictionary_id " +
                "LEFT JOIN card ON dc.card_id = card.id " +
                "WHERE dictionary.is_selected = 1 " +
                "GROUP BY dictionary.id"
    )
    fun getSelectedDictWithStats(
        statusUnknown: WordStatus = WordStatus.UNKNOWN,
        statusKnown: WordStatus = WordStatus.KNOWN,
        statusReadyToLearn: WordStatus = WordStatus.READY_TO_LEARN,
        statusLearning: WordStatus = WordStatus.LEARNING,
        statusLearned: WordStatus = WordStatus.LEARNED
    ): LiveData<List<DictionaryWithStatsTuple>>

    @Transaction
    @Query("SELECT * FROM dictionary WHERE id == :id")
    suspend fun getDictionaryById(id: Long): DictionaryRelation

    @Delete
    fun deleteDictionary(dictionary: DictionaryDbModel)

    @Transaction
    @Query("SELECT * FROM dictionary ORDER BY creation_date_time LIMIT 1")
    suspend fun getLastCreatedDictionary(): DictionaryRelation?

    @Query("SELECT * FROM dictionary WHERE name ==:name LIMIT 1")
    suspend fun getDictionaryByName(name: String): DictionaryDbModel?
}