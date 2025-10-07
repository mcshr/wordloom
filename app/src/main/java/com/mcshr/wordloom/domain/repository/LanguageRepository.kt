package com.mcshr.wordloom.domain.repository

import androidx.lifecycle.LiveData
import com.mcshr.wordloom.domain.entities.Language

interface LanguageRepository {
    fun getAllLanguages():LiveData<List<Language>>
    suspend fun getLanguagesCount():Int
}