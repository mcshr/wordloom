package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.WordloomRepository
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.entities.WordStatus

class CreateWordCardUseCase
    (private val repository: WordloomRepository) {
    operator fun invoke(word:String, translations: List<String>, partOfSpeech: String?, imagePath: String?){
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

        repository.createWordCard(wordCard)
    }
}