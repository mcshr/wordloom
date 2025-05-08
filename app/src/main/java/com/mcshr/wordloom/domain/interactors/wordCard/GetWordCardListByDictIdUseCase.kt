package com.mcshr.wordloom.domain.interactors.wordCard

import androidx.lifecycle.LiveData
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.repository.WordCardRepository
import javax.inject.Inject

class GetWordCardListByDictIdUseCase @Inject constructor(
    private val repository:WordCardRepository
) {
    operator fun invoke(dictId:Long): LiveData<List<WordCard>> {
        return repository.getWordCardListByDictId(dictId)
    }
}