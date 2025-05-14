package com.mcshr.wordloom.presentation.homeScreen.sessionSettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.domain.interactors.appSettings.GetSessionWordLimitUseCase
import com.mcshr.wordloom.domain.interactors.appSettings.SaveSessionWordLimitUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.AutoAddCardToLearnUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionSettingsViewModel @Inject constructor(
    getSessionWordLimitUseCase:GetSessionWordLimitUseCase,
    private val saveSessionWordLimitUseCase: SaveSessionWordLimitUseCase,
    private val autoAddCardToLearnUseCase: AutoAddCardToLearnUseCase
): ViewModel() {

    private val _wordLimit = MutableLiveData<Int>()
    val wordLimit : LiveData<Int>
        get() = _wordLimit

    init {
        _wordLimit.value = getSessionWordLimitUseCase()
    }

    fun setWordLimit(value:Int){
        if(_wordLimit.value != value)
            _wordLimit.value = value
    }

    fun saveSessionWordLimit() {
        wordLimit.value?.let {
            saveSessionWordLimitUseCase(it)
        }
    }



    fun startLearning(){
        saveSessionWordLimit()

    }
    fun autoAddCards(readyWordsCount:Int){
        saveSessionWordLimit()
        wordLimit.value?.let{
            val wordsToAdd = it - readyWordsCount
            viewModelScope.launch(Dispatchers.IO) {
                autoAddCardToLearnUseCase(wordsToAdd)
            }
        }
    }
}