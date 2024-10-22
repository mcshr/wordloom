package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.WordloomRepository
import com.mcshr.wordloom.domain.entities.WordCard

class AddWordCardUseCase
    (private val repository: WordloomRepository) {
    operator fun invoke(wordCard: WordCard){
        repository.addWordCard(wordCard)
    }
}