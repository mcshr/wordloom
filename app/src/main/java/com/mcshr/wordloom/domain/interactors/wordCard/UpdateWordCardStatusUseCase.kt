package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.entities.WordStatus
import javax.inject.Inject

class UpdateWordCardStatusUseCase @Inject constructor(
    private val updateWordCardInfoUseCase: UpdateWordCardInfoUseCase
) {
    suspend operator fun invoke( wordCard: WordCard, isPositiveAction: Boolean): Boolean {
        var newWordStatus = wordCard.status
        var newReviewCount = wordCard.reviewCount
        var moveCardToEnd = false
        if (isPositiveAction) {
            when (wordCard.status) {
                //WordStatus.UNKNOWN -> newWordStatus = WordStatus.READY_TO_LEARN
                WordStatus.READY_TO_LEARN -> newWordStatus = WordStatus.LEARNING
                WordStatus.LEARNING ->
                    if (wordCard.reviewCount < WordCard.MAX_REVIEW_COUNT) {
                        newReviewCount = wordCard.reviewCount + 1
                    } else {
                        newWordStatus = WordStatus.LEARNED
                        newReviewCount = wordCard.reviewCount + 1
                    }

                else -> throw RuntimeException("card with status ${wordCard.status}")
            }
        } else {
            moveCardToEnd = true
            when (wordCard.status) {
                //WordStatus.UNKNOWN -> newWordStatus = WordStatus.KNOWN
                WordStatus.READY_TO_LEARN -> {}
                WordStatus.LEARNING ->
                    if (wordCard.reviewCount in 3..4) {
                        newReviewCount = wordCard.reviewCount - 1
                    } else if (wordCard.reviewCount in 5..8) {
                        newReviewCount = wordCard.reviewCount - 2
                    }

                else -> throw RuntimeException("card with status ${wordCard.status}")
            }
        }
        val newReviewTime = CalculateNextReviewTimeUseCase()(newReviewCount)
        updateWordCardInfoUseCase(
            wordCard.copy(
                status = newWordStatus,
                reviewCount = newReviewCount,
                nextReviewTime = newReviewTime
            )
        )
        return moveCardToEnd
    }

}