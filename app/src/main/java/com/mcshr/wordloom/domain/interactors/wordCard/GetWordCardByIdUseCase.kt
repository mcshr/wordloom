package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.repository.WordCardRepository
import javax.inject.Inject

class GetWordCardByIdUseCase @Inject constructor(
    private val repository: WordCardRepository
) {
    suspend operator fun invoke(wordCardId:Long): WordCard{
        return repository.getWordCardById(wordCardId)
    }
}