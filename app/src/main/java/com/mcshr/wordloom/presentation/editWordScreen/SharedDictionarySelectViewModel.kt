package com.mcshr.wordloom.presentation.editWordScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.data.repository.AppSettingsRepositoryImpl
import com.mcshr.wordloom.data.repository.DictionaryRepositoryImpl
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.interactors.appSettings.GetSelectedDictionaryForWordUseCase
import com.mcshr.wordloom.domain.interactors.appSettings.SaveSelectedDictionaryForWordUseCase
import com.mcshr.wordloom.domain.interactors.dictionary.GetDictionaryUseCase
import kotlinx.coroutines.launch

class SharedDictionarySelectViewModel(application: Application):AndroidViewModel(application) {

    private val dictRepository = DictionaryRepositoryImpl(application)
    private val settingsRepository = AppSettingsRepositoryImpl(application)

    private val getDictionaryByIdUseCase = GetDictionaryUseCase(dictRepository)
    private val getSelectedDictionaryForWordUseCase = GetSelectedDictionaryForWordUseCase(
        settingsRepository,
        dictRepository
    )
    private val saveSelectedDictionaryForWordUseCase = SaveSelectedDictionaryForWordUseCase(
        settingsRepository
    )

    private val _selectedDictionary = MutableLiveData<Dictionary>()
    val selectedDictionary: LiveData<Dictionary>
        get() = _selectedDictionary


    private fun loadSelectedDictionary(){
        viewModelScope.launch {
            val dictId = getSelectedDictionaryForWordUseCase()
            val dict = dictId?.let{getDictionaryByIdUseCase(it)}
            dict?.let { _selectedDictionary.postValue(it) }
        }
    }
    fun selectDictionary(dict:Dictionary) {
        _selectedDictionary.value = dict
        saveSelectedDictionaryForWordUseCase(dict.id)
    }
    init {
        loadSelectedDictionary()
    }



}