package com.mcshr.wordloom.domain.interactors.dictionary

import com.mcshr.wordloom.domain.repository.DictionaryRepository
import javax.inject.Inject

class CheckIfAnyDictionaryExistsUseCase @Inject constructor(
    private val repository: DictionaryRepository
) {
    suspend operator fun invoke():Boolean{
        return repository.isAnyDictionaryExists()
    }
}