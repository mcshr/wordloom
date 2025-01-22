package com.mcshr.wordloom.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
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

    abstract fun appDao():WordloomDao
    abstract fun prepopulateDataDao():PrepopulateDataDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val DATABASE_NAME = "wordloom_db"
        fun getInstance(application: Application):AppDatabase{
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).addCallback(DatabaseCallback(application)).build()
                INSTANCE = db
                return db
            }
        }
    }
}