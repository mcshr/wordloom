package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.repository.WordCardRepository
import javax.inject.Inject

class UpdateWordCardInfoUseCase @Inject constructor(
    private val repository: WordCardRepository
) {
    suspend operator fun invoke(wordCard: WordCard) {
        repository.updateInfoWordCard(wordCard)
    }
}