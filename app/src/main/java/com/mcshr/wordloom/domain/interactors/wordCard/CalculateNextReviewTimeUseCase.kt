package com.mcshr.wordloom.domain.interactors.wordCard

import android.util.Log
import java.time.LocalDateTime
import java.time.ZoneOffset

class CalculateNextReviewTimeUseCase {
    operator fun invoke(countOfReviews: Int): Long {
        val now = LocalDateTime.now()
        val nextReviewTime = when (countOfReviews) {
            0 -> now.plusSeconds(1).also { Log.d("REVIEW", "0") }
            1 -> now.plusSeconds(2).also { Log.d("REVIEW", "1") }
            2 -> now.plusSeconds(5).also { Log.d("REVIEW", "2") }
            3 -> now.plusSeconds(5).also { Log.d("REVIEW", "3") }
            4 -> now.plusSeconds(5).also { Log.d("REVIEW", "4") }
            5 -> now.plusSeconds(10).also { Log.d("REVIEW", "5") }
            6 -> now.plusSeconds(10).also { Log.d("REVIEW", "6") }
            7 -> now.plusSeconds(5).also { Log.d("REVIEW", "7") }
            8 -> now.plusSeconds(10).also { Log.d("REVIEW", "8") }
 
//            0 -> now.plusMinutes(30)
//            1 -> now.plusHours(1)
//            2 -> now.plusHours(12)
//            3 -> now.plusDays(2)
//            4 -> now.plusDays(7)
//            5 -> now.plusDays(14)
//            6 -> now.plusDays(30)
//            7 -> now.plusDays(90)
//            8 -> now.plusDays(180)
            else -> now //TODO Ebbinghaus
        }
        return nextReviewTime.toEpochSecond(ZoneOffset.UTC) //Unix timestamp
    }
}