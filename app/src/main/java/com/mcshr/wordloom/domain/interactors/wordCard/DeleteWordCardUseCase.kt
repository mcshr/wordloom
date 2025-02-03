package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.repository.WordCardRepository
import com.mcshr.wordloom.domain.entities.WordCard

class DeleteWordCardUseCase
    (private val repository: WordCardRepository) {
    operator fun invoke(wordCard: WordCard){
        repository.deleteWordCard(wordCard)
    }
}