package com.mcshr.wordloom.data.database

import android.app.Application
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mcshr.wordloom.data.repository.PrepopulateDataRepositoryImpl
import com.mcshr.wordloom.domain.interactors.prepopulateData.PrepopulateLanguagesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseCallback(val application: Application): RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            val repository = PrepopulateDataRepositoryImpl(application)
            PrepopulateLanguagesUseCase(repository)()
        }
        super.onCreate(db)
    }
}