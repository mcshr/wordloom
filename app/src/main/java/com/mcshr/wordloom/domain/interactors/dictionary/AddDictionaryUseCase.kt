package com.mcshr.wordloom.domain.interactors.dictionary

import com.mcshr.wordloom.domain.WordloomRepository
import com.mcshr.wordloom.domain.entities.Dictionary

class AddDictionaryUseCase
    (private val repository: WordloomRepository){
    operator fun invoke(dictionary: Dictionary){
        repository.addDictionary(dictionary)
    }
}