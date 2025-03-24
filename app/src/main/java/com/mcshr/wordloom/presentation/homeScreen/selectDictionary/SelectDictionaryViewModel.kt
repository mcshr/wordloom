package com.mcshr.wordloom.presentation.homeScreen.selectDictionary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.data.repository.DictionaryRepositoryImpl
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.interactors.dictionary.EditDictionaryUseCase
import com.mcshr.wordloom.domain.interactors.dictionary.GetAllDictionariesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectDictionaryViewModel(application: Application):AndroidViewModel(application) {
    private val repository = DictionaryRepositoryImpl(application)
    private val getAllDictionariesUseCase = GetAllDictionariesUseCase(repository)
    private val editDictionaryUseCase = EditDictionaryUseCase(repository)

    val  allDictionaries = getAllDictionariesUseCase()

    fun selectDictionary(dictionary: Dictionary){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                editDictionaryUseCase(dictionary.copy(isSelected = !dictionary.isSelected))
            }
        }
    }
}