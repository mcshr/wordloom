package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.WordloomRepository
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.entities.WordStatus
import java.time.LocalDateTime

class UpdateWordCardStatusUseCase(private val repository: WordloomRepository) {
    operator fun invoke(wordCardId: Int, isPositiveAction: Boolean) {
        val wordCard = GetWordCardByIdUseCase(repository)(wordCardId)
        var newWordStatus: WordStatus = wordCard.status
        var newReviewCount: Int = wordCard.reviewCount
        if (isPositiveAction) {
            when (wordCard.status) {
                WordStatus.UNKNOWN -> newWordStatus = WordStatus.READY_TO_LEARN
                WordStatus.READY_TO_LEARN -> newWordStatus = WordStatus.LEARNING
                WordStatus.LEARNING ->
                    if (wordCard.reviewCount < WordCard.MAX_REVIEW_COUNT - 1) {
                        newReviewCount = wordCard.reviewCount + 1
                    } else {
                        newWordStatus = WordStatus.LEARNED
                        newReviewCount = wordCard.reviewCount + 1
                    }

                else -> throw RuntimeException("card with status ${wordCard.status}")
            }
        } else {
            when (wordCard.status) {
                WordStatus.UNKNOWN -> newWordStatus = WordStatus.KNOWN
                WordStatus.READY_TO_LEARN -> {}
                WordStatus.LEARNING ->
                    if (wordCard.reviewCount in 4..5) {
                        newReviewCount = wordCard.reviewCount - 1
                    } else if (wordCard.reviewCount in 6..8) {
                        newReviewCount = wordCard.reviewCount - 2
                    }

                else -> throw RuntimeException("card with status ${wordCard.status}")
            }
        }
        val newReviewTime: LocalDateTime = CalculateNextReviewTimeUseCase()(newReviewCount)
        EditWordCardUseCase(repository)(
            wordCard.copy(
                status = newWordStatus,
                reviewCount = newReviewCount,
                nextReviewTime = newReviewTime
            )
        )
    }

}