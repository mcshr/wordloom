package com.mcshr.wordloom.presentation.learningScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.interactors.appSettings.GetSessionWordLimitUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.GetLearningSetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearningViewModel @Inject constructor(
    private val getLearningSetUseCase: GetLearningSetUseCase,
    getSessionWordLimitUseCase: GetSessionWordLimitUseCase
) : ViewModel() {

    private val _learningSet =  MutableLiveData<List<WordCard>>()
    val learningSet : LiveData<List<WordCard>>
        get() = _learningSet

    init{
        viewModelScope.launch(Dispatchers.IO) {
            val wordLimit = getSessionWordLimitUseCase()
            _learningSet.postValue(getLearningSetUseCase(wordLimit))
        }
    }
}