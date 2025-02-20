package com.mcshr.wordloom.domain.interactors.dictionary

import com.mcshr.wordloom.domain.repository.DictionaryRepository

class CheckIfAnyDictionaryExistsUseCase(
    private val repository: DictionaryRepository
) {
    suspend operator fun invoke():Boolean{
        return repository.isAnyDictionaryExists()
    }
}