package com.mcshr.wordloom.domain.wrappers.errors

import com.mcshr.wordloom.domain.entities.WordCard

sealed class WordCardOperationFailure {
    data class DuplicateTranslation(val existingCard: WordCard) : WordCardOperationFailure()
    data object Unknown : WordCardOperationFailure()
}