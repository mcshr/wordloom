package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.entities.Language
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.entities.WordStatus
import com.mcshr.wordloom.domain.repository.WordCardRepository
import com.mcshr.wordloom.domain.wrappers.DataOperationState
import com.mcshr.wordloom.domain.wrappers.DataOperationState.Failure
import com.mcshr.wordloom.domain.wrappers.DataOperationState.Success
import com.mcshr.wordloom.domain.wrappers.errors.WordCardCreationFailure
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
    ): DataOperationState<Long, WordCardCreationFailure> {
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
            languageTranslation = languageTranslation
        )

        repository.getWordCardIfTranslationsExists(wordCard)?.let {
            return Failure(
                WordCardCreationFailure.DuplicateTranslation(it)
            )
        }
        val result = repository.createWordCard(wordCard)

        return if (result!=null){
            Success(result)
        } else {
            Failure(WordCardCreationFailure.Unknown)
        }
    }
}