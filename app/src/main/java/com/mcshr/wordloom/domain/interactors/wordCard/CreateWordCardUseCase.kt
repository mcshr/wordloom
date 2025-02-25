package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.entities.WordStatus
import com.mcshr.wordloom.domain.repository.WordCardRepository

class CreateWordCardUseCase
    (private val repository: WordCardRepository) {
    suspend operator fun invoke(word:String, translations: List<String>, partOfSpeech: String?, imagePath: String?):Boolean{
        val wordCard = WordCard(
            id = 0,
            wordText = word,
            wordTranslations = translations,
            status = WordStatus.UNKNOWN,
            reviewCount = 0,
            nextReviewTime = null,
            imagePath = imagePath,
            partOfSpeech = partOfSpeech
        )

        return repository.createWordCard(wordCard)
    }
}