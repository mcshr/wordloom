package com.mcshr.wordloom.data.database.converters

import android.util.Log
import androidx.room.TypeConverter
import com.mcshr.wordloom.domain.entities.WordStatus


class WordStatusConverter {
    @TypeConverter
    fun wordStatusToInt(wordStatus: WordStatus): Int {
        Log.d("WordStatusConverter", "Converting WordStatus: $wordStatus to Int: ${wordStatus.ordinal}")
        return wordStatus.ordinal
    }

    @TypeConverter
    fun intToWordStatus(index: Int): WordStatus {
        Log.d("WordStatusConverter", "Converting Int: $index to WordStatus: ${WordStatus.entries[index]}")
        return WordStatus.entries[index]
    }
}