package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.repository.WordCardRepository

class SaveWordCardToDictionaryUseCase(private val repository: WordCardRepository) {
    suspend operator fun invoke(dictionaryId:Long?, wordCardId: Long?):Boolean{
        if(dictionaryId == null || wordCardId == null)
            return false
        repository.saveWordCardToDictionary(dictionaryId, wordCardId)
        return true
    }
}