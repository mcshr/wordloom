package com.mcshr.wordloom.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.mcshr.wordloom.data.entities.LanguageDbModel

@Dao
interface LanguageDao {

    @Insert(onConflict = IGNORE)
    suspend fun insertLanguageList(languageList: List<LanguageDbModel>)

    @Query("SELECT * FROM language")
    fun getAllLanguages(): LiveData<List<LanguageDbModel>>

    @Query("SELECT COUNT(*) FROM language")
    suspend fun getLanguagesCount():Int
}