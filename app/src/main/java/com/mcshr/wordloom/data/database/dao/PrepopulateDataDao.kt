package com.mcshr.wordloom.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.mcshr.wordloom.data.entities.LanguageDbModel

@Dao
interface PrepopulateDataDao {

    @Insert
    suspend fun insertLanguageList(languageList: List<LanguageDbModel>)
}