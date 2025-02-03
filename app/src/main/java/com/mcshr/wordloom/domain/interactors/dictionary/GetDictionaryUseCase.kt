package com.mcshr.wordloom.domain.interactors.dictionary

import com.mcshr.wordloom.domain.repository.DictionaryRepository
import com.mcshr.wordloom.domain.entities.Dictionary

class GetDictionaryUseCase(
    val repository: DictionaryRepository
) {
    suspend operator fun invoke(dictionaryId: Long): Dictionary
    {
        return repository.getDictionary(dictionaryId)
    }
}