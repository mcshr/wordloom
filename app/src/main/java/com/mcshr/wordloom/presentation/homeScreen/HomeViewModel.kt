package com.mcshr.wordloom.presentation.homeScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mcshr.wordloom.data.repository.DictionaryRepositoryImpl
import com.mcshr.wordloom.data.repository.WordCardRepositoryImpl
import com.mcshr.wordloom.domain.interactors.dictionary.GetSelectedDictionariesWithStatsUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.GetReadyToRepeatCardsCountFromSelectedDictionariesUseCase

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val dictionaryRepository = DictionaryRepositoryImpl(application)
    private val wordCardRepository = WordCardRepositoryImpl(application)
    private val getSelectedDictionariesWithStatsUseCase =
        GetSelectedDictionariesWithStatsUseCase(dictionaryRepository)
    private val getReadyToRepeatCardsCountFromSelectedDictionariesUseCase =
        GetReadyToRepeatCardsCountFromSelectedDictionariesUseCase(wordCardRepository)

    val repeatCount = getReadyToRepeatCardsCountFromSelectedDictionariesUseCase()
    val selectedDictionaries = getSelectedDictionariesWithStatsUseCase()

    val stats: LiveData<Stats> = selectedDictionaries.map{ list->
        Stats(
            readyToLearn = list.sumOf { it.readyToLearnCountCards},
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