package com.mcshr.wordloom.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.domain.interactors.prepopulateData.PrepopulateLanguagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prepopulateLanguagesUseCase: PrepopulateLanguagesUseCase
): ViewModel() {

    fun prepopulateAllData(){
        viewModelScope.launch(Dispatchers.IO) {
            prepopulateLanguagesUseCase()
        }
    }
}