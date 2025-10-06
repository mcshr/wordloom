package com.mcshr.wordloom.domain.wrappers.errors

import com.mcshr.wordloom.domain.entities.WordCard

sealed class WordCardCreationFailure {
    data class DuplicateTranslation(val existingCard: WordCard) : WordCardCreationFailure()
    data object Unknown : WordCardCreationFailure()
}