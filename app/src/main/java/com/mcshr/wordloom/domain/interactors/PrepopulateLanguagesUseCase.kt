package com.mcshr.wordloom.domain.interactors

import com.mcshr.wordloom.domain.PrepopulateDataRepository
import com.mcshr.wordloom.domain.entities.Language

class PrepopulateLanguagesUseCase(
    val repository: PrepopulateDataRepository
) {
    suspend operator fun invoke(){
        val languages = listOf(
            Language("en", "English"),
            Language("ru", "Russian"),
            Language("ua", "Ukrainian")
        )
        repository.prepopulateLanguages(languages)
    }
}