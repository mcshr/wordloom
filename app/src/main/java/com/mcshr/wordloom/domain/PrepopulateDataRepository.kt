package com.mcshr.wordloom.domain

import com.mcshr.wordloom.domain.entities.Language

interface PrepopulateDataRepository {
    suspend fun prepopulateLanguages(languages: List<Language>)
}