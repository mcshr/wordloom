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
        observeRepeatCount()
    }

    suspend fun checkIfAnyDictionaryExist(): Boolean {
        return withContext(Dispatchers.IO) {
            checkIfAnyDictionaryExistsUseCase()
        }
    }

    private fun observeRepeatCount() {
        nextCheckTimer?.cancel()
        nextCheckTimer = viewModelScope.launch {
            while (isActive) {
                refreshRepeatCount()

                val now = System.currentTimeMillis()
                val nextReviewTime = getNextReviewTimeUseCase(1)?.firstOrNull()

                val delayMillis = when {
                    nextReviewTime == null -> DEFAULT_DELAY
                    nextReviewTime > now + DEFAULT_DELAY -> DEFAULT_DELAY
                    nextReviewTime < now + MIN_DELAY -> MIN_DELAY
                    else -> nextReviewTime - now
                }

                delay(delayMillis)
            }
        }
    }

    fun restartObserver() {
        observeRepeatCount()
    }

    private suspend fun refreshRepeatCount() {
        _repeatCount.postValue(getReadyToRepeatCardsCountFromSelectedDictsUseCase())
    }

    override fun onCleared() {
        super.onCleared()
        nextCheckTimer?.cancel()
    }

    companion object {
        private const val ONE_SECOND = 1000L
        private const val ONE_MINUTE = 60L * ONE_SECOND
        private const val DEFAULT_DELAY = 5 * ONE_MINUTE
        private const val MIN_DELAY = 5 * ONE_SECOND
    }
}