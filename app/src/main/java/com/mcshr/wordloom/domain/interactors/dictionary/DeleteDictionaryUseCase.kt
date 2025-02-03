package com.mcshr.wordloom.domain.interactors.dictionary

import com.mcshr.wordloom.domain.repository.DictionaryRepository
import com.mcshr.wordloom.domain.entities.Dictionary

class DeleteDictionaryUseCase
    (private val repository: DictionaryRepository){
    operator fun invoke(dictionary: Dictionary){
        repository.deleteDictionary(dictionary)
    }
}