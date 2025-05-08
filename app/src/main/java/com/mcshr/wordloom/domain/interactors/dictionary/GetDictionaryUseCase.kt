package com.mcshr.wordloom.domain.interactors.dictionary

import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.repository.DictionaryRepository
import javax.inject.Inject

class GetDictionaryUseCase @Inject constructor(
    private val repository: DictionaryRepository
) {
    suspend operator fun invoke(dictionaryId: Long): Dictionary
    {
        return repository.getDictionary(dictionaryId)
    }
}