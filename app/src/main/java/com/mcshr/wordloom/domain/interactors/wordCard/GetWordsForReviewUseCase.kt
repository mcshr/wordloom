package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.WordloomRepository
import com.mcshr.wordloom.domain.entities.WordCard
import java.time.LocalDateTime

class GetWordsForReviewUseCase(private val repository: WordloomRepository) {
    operator fun invoke():List<WordCard>{
        val currentTime = LocalDateTime.now()
        return repository.getWordsForReview(currentTime)
    }
}