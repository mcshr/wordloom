package com.mcshr.wordloom.domain.interactors.prepopulateData

import com.mcshr.wordloom.domain.entities.Language
import com.mcshr.wordloom.domain.repository.PrepopulateDataRepository

class PrepopulateLanguagesUseCase(
    val repository: PrepopulateDataRepository
) {
    suspend operator fun invoke(){
        val languages = listOf(
            Language("en", "English", 0),
            Language("ru", "Russian", 0),
            Language("ua", "Ukrainian", 0)
        )
        repository.prepopulateLanguages(languages)
    }
}