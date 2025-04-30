package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.entities.WordStatus
import com.mcshr.wordloom.domain.repository.WordCardRepository

class ToggleReadyToLearnStateUseCase(private val repository: WordCardRepository) {
    suspend operator fun invoke(wordCard: WordCard){
        when(wordCard.status){
            WordStatus.UNKNOWN -> {
                wordCard.setStatus(WordStatus.READY_TO_LEARN)
            }
            WordStatus.READY_TO_LEARN -> {
                wordCard.setStatus(WordStatus.UNKNOWN)
            }
            else -> {}
        }
    }
    private suspend fun WordCard.setStatus(wordStatus: WordStatus){
        repository.editWordCard(
           this.copy(
                status = wordStatus
            )
        )
    }
}