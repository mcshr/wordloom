package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.entities.WordStatus
import com.mcshr.wordloom.domain.repository.WordCardRepository
import javax.inject.Inject

class AutoAddCardToLearnUseCase @Inject constructor(
    private val wordCardRepository: WordCardRepository
) {
    suspend operator fun invoke(wordsToAddCount:Int){
        if(wordsToAddCount<=0) return
        val cardsToUpdate = wordCardRepository
            .getWordCardsByStatusFromSelectedDictionaries(WordStatus.UNKNOWN)
            .sortedBy { it.id }
            .take(wordsToAddCount)
            .map{it.copy(status = WordStatus.READY_TO_LEARN)}


        wordCardRepository.updateInfoWordCardList(cardsToUpdate)

    }
}