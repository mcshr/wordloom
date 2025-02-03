package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.repository.WordCardRepository
import com.mcshr.wordloom.domain.entities.WordCard

class GetWordCardByIdUseCase(private val repository: WordCardRepository) {
    operator fun invoke(wordCardId: Int):WordCard{
        return repository.getWordCardById(wordCardId)
    }
}