package com.mcshr.wordloom.presentation.homeScreen.selectDictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.interactors.dictionary.EditDictionaryUseCase
import com.mcshr.wordloom.domain.interactors.dictionary.GetAllDictionariesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SelectDictionaryViewModel @Inject constructor(
    getAllDictionariesUseCase: GetAllDictionariesUseCase,
    private val editDictionaryUseCase: EditDictionaryUseCase
) : ViewModel() {

    val allDictionaries = getAllDictionariesUseCase()

    fun selectDictionary(dictionary: Dictionary) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                editDictionaryUseCase(dictionary.copy(isSelected = !dictionary.isSelected))
            }
        }
    }
}