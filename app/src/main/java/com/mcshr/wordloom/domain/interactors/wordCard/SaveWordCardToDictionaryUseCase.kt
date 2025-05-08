package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.repository.WordCardRepository
import javax.inject.Inject

class SaveWordCardToDictionaryUseCase @Inject constructor(
    private val repository: WordCardRepository
) {
    suspend operator fun invoke(dictionaryId: Long, wordCardId: Long?): Boolean {
        if (wordCardId == null)
            return false
        repository.saveWordCardToDictionary(dictionaryId, wordCardId)
        return true
    }
}