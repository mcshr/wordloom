package com.mcshr.wordloom.domain.repository

import com.mcshr.wordloom.domain.entities.Language

interface PrepopulateDataRepository {
    suspend fun prepopulateLanguages(languages: List<Language>)
    suspend fun hasLanguages():Boolean
}