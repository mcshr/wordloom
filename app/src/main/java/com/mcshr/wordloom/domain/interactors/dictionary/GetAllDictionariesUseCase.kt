package com.mcshr.wordloom.domain.interactors.dictionary

import androidx.lifecycle.LiveData
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.repository.DictionaryRepository
import javax.inject.Inject

class GetAllDictionariesUseCase @Inject constructor(
    private val repository: DictionaryRepository
) {
    operator fun invoke(): LiveData<List<Dictionary>> {
        return repository.getAllDictionaries()
    }
}