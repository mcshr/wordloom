package com.mcshr.wordloom.presentation.homeScreen.sessionSettings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mcshr.wordloom.data.repository.AppSettingsRepositoryImpl
import com.mcshr.wordloom.domain.interactors.appSettings.GetSessionWordLimitUseCase
import com.mcshr.wordloom.domain.interactors.appSettings.SaveSessionWordLimitUseCase

class SessionSettingsViewModel(application: Application):AndroidViewModel(application) {
    private val settingsRepository = AppSettingsRepositoryImpl(application)
    private val getSessionWordLimitUseCase = GetSessionWordLimitUseCase(settingsRepository)
    private val saveSessionWordLimitUseCase = SaveSessionWordLimitUseCase(settingsRepository)

    private val _wordLimit = MutableLiveData<Int>()
    val wordLimit : LiveData<Int>
        get() = _wordLimit

    fun setWordLimit(value:Int){
        if(_wordLimit.value != value)
            _wordLimit.value = value
    }

    fun saveSessionWordLimit(wordLimit:Int){
        saveSessionWordLimitUseCase(wordLimit)
    }
    fun getSessionWordLimit():Int{
        return getSessionWordLimitUseCase()
    }
}