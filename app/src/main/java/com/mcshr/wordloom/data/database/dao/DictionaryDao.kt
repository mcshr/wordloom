package com.mcshr.wordloom.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mcshr.wordloom.data.entities.DictionaryDbModel

@Dao
interface DictionaryDao {
    @Insert
    suspend fun createDictionary(dictionary: DictionaryDbModel)

    @Update
    fun editDictionary(dictionary: DictionaryDbModel)

    @Query("SELECT * FROM dictionary WHERE is_selected == 1")
    fun getSelectedDictionaries(): LiveData<List<DictionaryDbModel>>

    @Query("SELECT * FROM dictionary")
    fun getAllDictionaries(): LiveData<List<DictionaryDbModel>>

    @Query("SELECT * FROM dictionary WHERE id == :id")
    suspend fun getDictionaryById(id:Long): DictionaryDbModel

    @Delete
    fun deleteDictionary(dictionary: DictionaryDbModel)

    @Query("SELECT * FROM dictionary ORDER BY creation_date_time LIMIT 1")
    suspend fun getLastCreatedDictionary(): DictionaryDbModel?
}