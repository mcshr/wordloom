package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.WordloomRepository
import com.mcshr.wordloom.domain.entities.WordCard

class DeleteWordCardUseCase
    (private val repository: WordloomRepository) {
    operator fun invoke(wordCard: WordCard){
        repository.deleteWordCard(wordCard)
    }
}