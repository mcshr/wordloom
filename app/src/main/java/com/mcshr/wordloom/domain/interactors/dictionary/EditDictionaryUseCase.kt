package com.mcshr.wordloom.domain.interactors.dictionary

import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.repository.DictionaryRepository

class EditDictionaryUseCase
    (private val repository: DictionaryRepository) {
    suspend operator fun invoke(dictionary: Dictionary) {
        repository.editDictionary(dictionary)
    }
}