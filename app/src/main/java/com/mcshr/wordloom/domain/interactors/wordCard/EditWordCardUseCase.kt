package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.repository.WordCardRepository
import com.mcshr.wordloom.domain.wrappers.DataOperationState
import com.mcshr.wordloom.domain.wrappers.errors.WordCardOperationFailure
import javax.inject.Inject

class EditWordCardUseCase @Inject constructor(
    private val repository: WordCardRepository
) {
    suspend operator fun invoke(
        oldWordCard: WordCard,
        newWordCard: WordCard
    ): DataOperationState<WordCard, WordCardOperationFailure>{
        val existingWordCard = repository.getWordCardIfTranslationsExists(newWordCard)
        existingWordCard?.let{
            if(existingWordCard.wordText != oldWordCard.wordText){
                return DataOperationState.Failure(
                    WordCardOperationFailure.DuplicateTranslation(
                        existingWordCard
                    )
                )
            }
        }
        repository.editWordCard(newWordCard)
        return DataOperationState.Success()
    }
}