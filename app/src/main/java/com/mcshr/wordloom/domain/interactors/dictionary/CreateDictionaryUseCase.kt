package com.mcshr.wordloom.domain.interactors.dictionary

import com.mcshr.wordloom.domain.repository.DictionaryRepository
import com.mcshr.wordloom.domain.entities.Dictionary

class CreateDictionaryUseCase
    (private val repository: DictionaryRepository){
    suspend operator fun invoke(name:String, description:String?, imagePath:String?){
        val dictionary = Dictionary(
            id = 0, //default id
            name = name,
            description = description,
            imagePath = imagePath,
            isSelected = false,
            creationDateTime = System.currentTimeMillis() / 1000L //Unix timestamp
        )
        repository.createDictionary(dictionary)
    }
}