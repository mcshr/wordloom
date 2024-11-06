package com.mcshr.wordloom.data

import androidx.room.TypeConverter
import com.mcshr.wordloom.domain.entities.WordStatus


class WordStatusConverter {
    @TypeConverter
    fun wordStatusToInt(wordStatus: WordStatus) = wordStatus.ordinal

    @TypeConverter
    fun intToWordStatus(index: Int) = WordStatus.entries[index]
}