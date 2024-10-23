package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.WordloomRepository
import com.mcshr.wordloom.domain.entities.WordCard

class GetWordsForReviewUseCase(private val repository: WordloomRepository) {
    operator fun invoke():List<WordCard>{
        val currentTime = System.currentTimeMillis() / 1000L //Unix timestamp
        return repository.getWordsForReview(currentTime)
    }
}