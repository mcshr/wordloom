package com.mcshr.wordloom.domain.interactors.dictionary

import com.mcshr.wordloom.domain.WordloomRepository
import com.mcshr.wordloom.domain.entities.Dictionary

class GetDictionaryUseCase(
    val repository: WordloomRepository
) {
    suspend operator fun invoke(dictionaryId: Long): Dictionary
    {
        return repository.getDictionary(dictionaryId)
    }
}