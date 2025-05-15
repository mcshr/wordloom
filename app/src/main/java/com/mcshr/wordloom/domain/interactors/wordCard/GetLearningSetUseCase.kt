package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.entities.WordStatus
import com.mcshr.wordloom.domain.repository.WordCardRepository
import javax.inject.Inject

class GetLearningSetUseCase @Inject constructor(
    private val repository: WordCardRepository
) {
    suspend operator fun invoke(wordLimit: Int): List<WordCard> {
        val currentTime = System.currentTimeMillis() / 1000L //Unix timestamp
        val learningSet = repository.getWordCardsForReview(currentTime, wordLimit)
        val setSize = learningSet.size
        if (setSize < wordLimit) {
            return learningSet + repository.getWordCardsByStatusFromSelectedDictionaries(
                WordStatus.READY_TO_LEARN,
                limit = wordLimit - setSize
            )
        }
        return learningSet
    }
}