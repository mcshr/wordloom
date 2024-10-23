package com.mcshr.wordloom.domain.interactors.wordCard

import java.time.LocalDateTime
import java.time.ZoneOffset

class CalculateNextReviewTimeUseCase {
    operator fun invoke(countOfReviews:Int):Long{
        val now = LocalDateTime.now()
        val nextReviewTime = when(countOfReviews){
            0 -> now.plusHours(1)
            1 -> now.plusHours(2)
            2 -> now.plusDays(1)
            else -> now //TODO Ebbinghaus
        }
        return nextReviewTime.toEpochSecond(ZoneOffset.UTC) //Unix timestamp
    }
}