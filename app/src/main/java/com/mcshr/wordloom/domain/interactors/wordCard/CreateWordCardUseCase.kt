package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.entities.Language
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.entities.WordStatus
import com.mcshr.wordloom.domain.repository.WordCardRepository
import javax.inject.Inject

class CreateWordCardUseCase @Inject constructor(
    private val repository: WordCardRepository
) {
    suspend operator fun invoke(
        word: String,
        translations: List<String>,
        partOfSpeech: String?,
        imagePath: String?,
        languageOriginal: Language,
        languageTranslation: Language
    ): Long? {
        val wordCard = WordCard(
            id = 0,
            wordText = word,
            wordTranslations = translations,
            status = WordStatus.UNKNOWN,
            reviewCount = 0,
            nextReviewTime = null,
            imagePath = imagePath,
            partOfSpeech = partOfSpeech,
            languageOriginal = languageOriginal,
            languageTranslation = languageTranslation,
            wordId = -1 //Default value
        )

        return repository.createWordCard(wordCard)
    }
}