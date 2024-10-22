package com.mcshr.wordloom.domain.interactors.dictionary

import com.mcshr.wordloom.domain.WordloomRepository
import com.mcshr.wordloom.domain.entities.Dictionary

class EditDictionaryUseCase
    (private val repository: WordloomRepository) {
    operator fun invoke(dictionary: Dictionary) {
        repository.editDictionary(dictionary)
    }
}