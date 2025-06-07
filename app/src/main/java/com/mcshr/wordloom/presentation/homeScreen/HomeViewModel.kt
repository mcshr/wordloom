package com.mcshr.wordloom.presentation.homeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.domain.interactors.dictionary.CheckIfAnyDictionaryExistsUseCase
import com.mcshr.wordloom.domain.interactors.dictionary.GetSelectedDictionariesWithStatsUseCase
import com.mcshr.wordloom.domain.interactors.prepopulateData.PrepopulateLanguagesUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.GetCardNextRepeatTimeUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.GetReadyToRepeatCardsCountFromSelectedDictsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getSelectedDictionariesWithStatsUseCase: GetSelectedDictionariesWithStatsUseCase,
    private val getReadyToRepeatCardsCountFromSelectedDictsUseCase: GetReadyToRepeatCardsCountFromSelectedDictsUseCase,
    private val checkIfAnyDictionaryExistsUseCase: CheckIfAnyDictionaryExistsUseCase,
    private val getNextReviewTimeUseCase: GetCardNextRepeatTimeUseCase,
    prepopulateLanguagesUseCase: PrepopulateLanguagesUseCase
) : ViewModel() {

    private val _repeatCount = MutableLiveData<Int>()
    val repeatCount: LiveData<Int> = _repeatCount

    val selectedDictionaries = getSelectedDictionariesWithStatsUseCase()
    private var nextCheckTimer: Job? = null

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
        refreshRepeatCount()
        observeRepeatCount()
    }

    suspend fun checkIfAnyDictionaryExist():Boolean{
        return withContext(Dispatchers.IO) {
             checkIfAnyDictionaryExistsUseCase()
        }
    }

    private fun observeRepeatCount(){
        nextCheckTimer?.cancel()
        nextCheckTimer = viewModelScope.launch {
            while (isActive){
                val nextReviewTime = getNextReviewTimeUseCase(2)
                val now = System.currentTimeMillis()
                nextReviewTime?: continue
                if( nextReviewTime.firstOrNull() == null
                    || nextReviewTime.first() > now+ONE_MINUTE) {
                    refreshRepeatCount()
                    delay(ONE_MINUTE)
                    continue
                }
                if (nextReviewTime.first() - nextReviewTime.last() < 10*ONE_SECOND){
                    delay(10*ONE_SECOND)
                    refreshRepeatCount()
                }
                val delayMillis = (nextReviewTime.first() - now).coerceAtLeast(0)
                delay(delayMillis)
            }
        }
    }

    private fun refreshRepeatCount(){
        viewModelScope.launch {
            _repeatCount.postValue(getReadyToRepeatCardsCountFromSelectedDictsUseCase())
        }
    }
    override fun onCleared() {
        super.onCleared()
        nextCheckTimer?.cancel()
    }

    companion object{
        const val ONE_SECOND = 1000L
        const val ONE_MINUTE = 60L * ONE_SECOND
        const val ONE_HOUR = 60L * ONE_MINUTE
    }
}