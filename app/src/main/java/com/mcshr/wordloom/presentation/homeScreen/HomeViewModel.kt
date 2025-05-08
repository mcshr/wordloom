package com.mcshr.wordloom.presentation.homeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.mcshr.wordloom.domain.interactors.dictionary.GetSelectedDictionariesWithStatsUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.GetReadyToRepeatCardsCountFromSelectedDictsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getSelectedDictionariesWithStatsUseCase: GetSelectedDictionariesWithStatsUseCase,
    getReadyToRepeatCardsCountFromSelectedDictsUseCase: GetReadyToRepeatCardsCountFromSelectedDictsUseCase
) : ViewModel() {
    val repeatCount = getReadyToRepeatCardsCountFromSelectedDictsUseCase()
    val selectedDictionaries = getSelectedDictionariesWithStatsUseCase()

    val stats: LiveData<Stats> = selectedDictionaries.map { list ->
        Stats(
            readyToLearn = list.sumOf { it.readyToLearnCountCards },
            total = list.sumOf { it.totalCountCards },
            learned = list.sumOf { it.learnedCountCards },
            unknown = list.sumOf { it.unknownCountCards },
            learning = list.sumOf { it.learningCountCards }
        )
    }

    data class Stats(
        val readyToLearn: Int,
        val total: Int,
        val learned: Int,
        val unknown: Int,
        val learning: Int
    )

}