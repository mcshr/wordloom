package com.mcshr.wordloom.presentation.homeScreen.sessionSettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mcshr.wordloom.domain.interactors.appSettings.GetSessionWordLimitUseCase
import com.mcshr.wordloom.domain.interactors.appSettings.SaveSessionWordLimitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionSettingsViewModel @Inject constructor(
    private val getSessionWordLimitUseCase:GetSessionWordLimitUseCase,
    private val saveSessionWordLimitUseCase: SaveSessionWordLimitUseCase
): ViewModel() {
    private val _wordLimit = MutableLiveData<Int>()
    val wordLimit : LiveData<Int>
        get() = _wordLimit

    fun setWordLimit(value:Int){
        if(_wordLimit.value != value)
            _wordLimit.value = value
    }

    fun saveSessionWordLimit(){
        wordLimit.value?.let {
            saveSessionWordLimitUseCase(it)
        }
    }
    fun getSessionWordLimit():Int{
        return getSessionWordLimitUseCase()
    }

    fun startLearning(){
        saveSessionWordLimit()

    }
    fun autoAddCards(){
        saveSessionWordLimit()

    }
}