package com.mcshr.wordloom.presentation.editDictionaryScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.data.WordloomRepositoryImpl
import com.mcshr.wordloom.domain.interactors.dictionary.CreateDictionaryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditDictionaryViewModel(application: Application): AndroidViewModel(application) {
    private val repository = WordloomRepositoryImpl(application)
    private val createDictionaryUseCase = CreateDictionaryUseCase(repository)

    //TODO validation, ready to close
    fun createDictionary(name: String){
        if (name.isNotEmpty())
            viewModelScope.launch {
                withContext(Dispatchers.IO){
                    createDictionaryUseCase(name, null, null)
                }
            }
    }
}