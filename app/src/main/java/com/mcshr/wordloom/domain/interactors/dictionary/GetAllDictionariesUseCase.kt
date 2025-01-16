package com.mcshr.wordloom.domain.interactors.dictionary

import androidx.lifecycle.LiveData
import com.mcshr.wordloom.domain.WordloomRepository
import com.mcshr.wordloom.domain.entities.Dictionary

class GetAllDictionariesUseCase(
    private val repository: WordloomRepository
) {
    operator fun invoke():LiveData<List<Dictionary>>{
        return repository.getAllDictionaries()
    }
}