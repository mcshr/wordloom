package com.mcshr.wordloom.domain.interactors.wordCard

import com.mcshr.wordloom.domain.repository.WordCardRepository
import javax.inject.Inject


class GetCardNextRepeatTimeUseCase @Inject constructor(
    private val wordCardRepository: WordCardRepository
) {
    suspend operator fun invoke(limit:Int): List<Long>?{
        return wordCardRepository.getNextRepeatTime(
            currentTimeUnix = System.currentTimeMillis() / 1000L,
            limit
        )
    }
}