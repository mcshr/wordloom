package com.mcshr.wordloom.domain.interactors.dictionary

import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.entities.Language
import com.mcshr.wordloom.domain.repository.DictionaryRepository
import javax.inject.Inject

class CreateDictionaryUseCase
@Inject constructor(private val repository: DictionaryRepository) {
    suspend operator fun invoke(
        name: String,
        description: String?,
        imagePath: String?,
        languageOriginal: Language,
        languageTranslation: Language
    ): Boolean {
        val dictionary = Dictionary(
            id = 0, //default id
            name = name,
            description = description,
            imagePath = imagePath,
            isSelected = false,
            creationDateTime = System.currentTimeMillis() / 1000L, //Unix timestamp
            languageOriginal = languageOriginal,
            languageTranslation = languageTranslation
        )
        return repository.createDictionary(dictionary)
    }
}