package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.entities.WordStatus
import javax.inject.Inject

class UpdateWordCardStatusUseCase @Inject constructor(
    private val editWordCardUseCase: EditWordCardUseCase
) {
    suspend operator fun invoke( wordCard: WordCard, isPositiveAction: Boolean) {
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
        val newReviewTime = CalculateNextReviewTimeUseCase()(newReviewCount)
        editWordCardUseCase(
            wordCard.copy(
                status = newWordStatus,
                reviewCount = newReviewCount,
                nextReviewTime = newReviewTime
            )
        )
    }

}