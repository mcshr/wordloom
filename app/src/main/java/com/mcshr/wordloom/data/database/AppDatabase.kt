package com.mcshr.wordloom.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mcshr.wordloom.data.database.dao.DictionaryDao
import com.mcshr.wordloom.data.database.dao.LanguageDao
import com.mcshr.wordloom.data.database.dao.WordCardDao
import com.mcshr.wordloom.data.entities.CardDbModel
import com.mcshr.wordloom.data.entities.CardTranslationDbModel
import com.mcshr.wordloom.data.entities.DictionaryCardDbModel
import com.mcshr.wordloom.data.entities.DictionaryDbModel
import com.mcshr.wordloom.data.entities.LanguageDbModel
import com.mcshr.wordloom.data.entities.TranslationDbModel
import com.mcshr.wordloom.data.entities.WordDbModel
import com.mcshr.wordloom.data.entities.tuples.SelectedDictionaryCardView

@Database(
    version = 1,
    entities = [
        CardDbModel::class,
        CardTranslationDbModel::class,
        DictionaryCardDbModel::class,
        DictionaryDbModel::class,
        LanguageDbModel::class,
        TranslationDbModel::class,
        WordDbModel::class
    ],
    views = [
        SelectedDictionaryCardView::class
            ],
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun wordCardDao(): WordCardDao
    abstract fun languageDao(): LanguageDao
    abstract fun dictionaryDao(): DictionaryDao
}