package com.mcshr.wordloom.data.entities.tuples

import com.mcshr.wordloom.domain.entities.WordStatus

data class WordCardListItem(
    val cardId: Int,
    val word: String,
    val translations: List<String>,
    val status: WordStatus,
    val nextReviewTime: Long?,
    val reviewsCount: Int
)


