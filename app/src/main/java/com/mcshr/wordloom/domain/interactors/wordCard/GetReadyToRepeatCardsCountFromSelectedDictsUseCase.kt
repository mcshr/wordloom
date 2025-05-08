package com.mcshr.wordloom.domain.interactors.wordCard

import androidx.lifecycle.LiveData
import com.mcshr.wordloom.domain.repository.WordCardRepository
import javax.inject.Inject

class GetReadyToRepeatCardsCountFromSelectedDictsUseCase @Inject constructor(
    private val repository: WordCardRepository
) {
    operator fun invoke():LiveData<Int>{
        return repository.getReadyToRepeatCardsCountFromSelectedDictionaries(
            currentTimeUnix = System.currentTimeMillis() / 1000L
        )
    }
}