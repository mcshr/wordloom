package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.WordloomRepository
import com.mcshr.wordloom.domain.entities.WordCard

class GetWordCardByIdUseCase(private val repository: WordloomRepository) {
    operator fun invoke(wordCardId: Int):WordCard{
        return repository.getWordCardById(wordCardId)
    }
}