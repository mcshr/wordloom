package com.mcshr.wordloom.domain.interactors.dictionary

import com.mcshr.wordloom.domain.WordloomRepository
import com.mcshr.wordloom.domain.entities.Dictionary

class DeleteDictionaryUseCase
    (private val repository: WordloomRepository){
    operator fun invoke(dictionary: Dictionary){
        repository.deleteDictionary(dictionary)
    }
}