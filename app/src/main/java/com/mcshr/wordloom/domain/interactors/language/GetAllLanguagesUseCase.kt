package com.mcshr.wordloom.domain.interactors.language

import androidx.lifecycle.LiveData
import com.mcshr.wordloom.domain.entities.Language
import com.mcshr.wordloom.domain.repository.LanguageRepository
import javax.inject.Inject

class GetAllLanguagesUseCase @Inject constructor(
    private val repository: LanguageRepository
) {
    operator fun invoke():LiveData<List<Language>>{
        return repository.getAllLanguages()
    }
}