package com.mcshr.wordloom.presentation.editWordScreen.selectDictionary

import androidx.lifecycle.ViewModel
import com.mcshr.wordloom.domain.interactors.dictionary.GetAllDictionariesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectDictionaryViewModel @Inject constructor(
    getAllDictionariesUseCase: GetAllDictionariesUseCase
) : ViewModel() {
    val allDictionaries = getAllDictionariesUseCase()
}