package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.repository.WordCardRepository
import javax.inject.Inject

class GetWordsForReviewUseCase @Inject constructor(
    private val repository: WordCardRepository
) {
    operator fun invoke(): List<WordCard> {
        val currentTime = System.currentTimeMillis() / 1000L //Unix timestamp
        return repository.getWordsForReview(currentTime)
    }
}