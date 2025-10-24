package com.mcshr.wordloom.domain.interactors.wordCard

import android.util.Log
import java.time.LocalDateTime
import java.time.ZoneOffset

class CalculateNextReviewTimeUseCase {
    operator fun invoke(countOfReviews: Int): Long {
        val now = LocalDateTime.now()
        val nextReviewTime = when (countOfReviews) {
            0 -> now.plusMinutes(30).also { Log.d("REVIEW", "0") }
            1 -> now.plusHours(3).also { Log.d("REVIEW", "1") }
            2 -> now.plusHours(12).also { Log.d("REVIEW", "2") }
            3 -> now.plusDays(1).also { Log.d("REVIEW", "3") }
            4 -> now.plusDays(3).also { Log.d("REVIEW", "4") }
            5 -> now.plusDays(7).also { Log.d("REVIEW", "5") }
            6 -> now.plusDays(14).also { Log.d("REVIEW", "6") }
            7 -> now.plusDays(30).also { Log.d("REVIEW", "7") }
            8 -> now.plusDays(90).also { Log.d("REVIEW", "8") }
            else -> now //TODO custom review time
        }
        return nextReviewTime.toEpochSecond(ZoneOffset.UTC) //Unix timestamp
    }
}