package com.mcshr.wordloom.presentation.editWordScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.interactors.appSettings.GetSelectedDictionaryForWordUseCase
import com.mcshr.wordloom.domain.interactors.appSettings.SaveSelectedDictionaryForWordUseCase
import com.mcshr.wordloom.domain.interactors.dictionary.GetDictionaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedDictionarySelectViewModel @Inject constructor(
    private val getDictionaryByIdUseCase: GetDictionaryUseCase,
    private val getSelectedDictionaryForWordUseCase: GetSelectedDictionaryForWordUseCase,
    private val saveSelectedDictionaryForWordUseCase: SaveSelectedDictionaryForWordUseCase
) : ViewModel() {

    private val _selectedDictionary = MutableLiveData<Dictionary>()
    val selectedDictionary: LiveData<Dictionary>
        get() = _selectedDictionary

    init {
        loadSelectedDictionary()
    }

    private fun loadSelectedDictionary() {
        viewModelScope.launch {
            val dictId = getSelectedDictionaryForWordUseCase()
            val dict = dictId?.let { getDictionaryByIdUseCase(it) }
            dict?.let { _selectedDictionary.postValue(it) }
        }
    }

    fun selectDictionary(dict: Dictionary) {
        _selectedDictionary.value = dict
        saveSelectedDictionaryForWordUseCase(dict.id)
    }

}