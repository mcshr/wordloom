package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.repository.WordCardRepository
import javax.inject.Inject

class GetReadyToRepeatCardsCountFromSelectedDictsUseCase @Inject constructor(
    private val repository: WordCardRepository
) {
    suspend operator fun invoke():Int{
        return repository.getRepeatCardsCountFromSelectedDictionaries(
            currentTimeUnix = System.currentTimeMillis() / 1000L
        )
    }
}