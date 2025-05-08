package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.repository.WordCardRepository
import javax.inject.Inject

class DeleteWordCardUseCase @Inject constructor(
    private val repository: WordCardRepository
) {
    operator fun invoke(wordCard: WordCard) {
        repository.deleteWordCard(wordCard)
    }
}