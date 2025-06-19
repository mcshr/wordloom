package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.repository.WordCardRepository
import javax.inject.Inject

class DeleteWordCardUseCase @Inject constructor(
    private val repository: WordCardRepository
) {
    suspend operator fun invoke(wordCard: WordCard, dictionaryId:Long) {
        val dictsCount = repository.getDictionaryCountForWordCard(wordCard.id)
        if(dictsCount>1){
            repository.removeWordCardFromDictionary(wordCard.id, dictionaryId)
        } else {
            repository.deleteWordCard(wordCard)
        }
    }
}