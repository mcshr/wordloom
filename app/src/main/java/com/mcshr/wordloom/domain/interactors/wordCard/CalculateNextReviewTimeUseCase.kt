package com.mcshr.wordloom.domain.interactors.wordCard

import java.time.LocalDateTime

class CalculateNextReviewTimeUseCase {
    operator fun invoke(countOfReviews:Int):LocalDateTime{
        val now = LocalDateTime.now()
        return when(countOfReviews){
            0 -> now.plusHours(1)
            1 -> now.plusHours(2)
            2 -> now.plusDays(1)
            else -> now //TODO Ebbinghaus
        }
    }
}