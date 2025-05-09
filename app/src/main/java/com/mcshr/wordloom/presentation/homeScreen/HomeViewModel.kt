package com.mcshr.wordloom.presentation.homeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.domain.interactors.dictionary.CheckIfAnyDictionaryExistsUseCase
import com.mcshr.wordloom.domain.interactors.dictionary.GetSelectedDictionariesWithStatsUseCase
import com.mcshr.wordloom.domain.interactors.prepopulateData.PrepopulateLanguagesUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.GetReadyToRepeatCardsCountFromSelectedDictsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getSelectedDictionariesWithStatsUseCase: GetSelectedDictionariesWithStatsUseCase,
    getReadyToRepeatCardsCountFromSelectedDictsUseCase: GetReadyToRepeatCardsCountFromSelectedDictsUseCase,
    private val checkIfAnyDictionaryExistsUseCase: CheckIfAnyDictionaryExistsUseCase,
    prepopulateLanguagesUseCase: PrepopulateLanguagesUseCase
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
    init {
        viewModelScope.launch(Dispatchers.IO) {
            prepopulateLanguagesUseCase()
        }
    }

    suspend fun checkIfAnyDictionaryExist():Boolean{
        return withContext(Dispatchers.IO) {
             checkIfAnyDictionaryExistsUseCase()
        }
    }
}