package com.mcshr.wordloom.presentation.createDictionaryScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.domain.entities.Language
import com.mcshr.wordloom.domain.interactors.dictionary.CreateDictionaryUseCase
import com.mcshr.wordloom.domain.interactors.language.GetAllLanguagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateDictionaryViewModel @Inject constructor(
    private val createDictionaryUseCase: CreateDictionaryUseCase,
    getAllLanguagesUseCase: GetAllLanguagesUseCase
) : ViewModel() {

    private val _saveAndClose = MutableLiveData<Boolean>()
    val saveAndClose: LiveData<Boolean>
        get() = _saveAndClose

    val allLanguages = getAllLanguagesUseCase()

    fun createDictionary(name: String, languageWord: Language, languageMeaning: Language) {
        viewModelScope.launch {
            val isSuccess = withContext(Dispatchers.IO) {
                createDictionaryUseCase(
                    name, null, null, languageWord, languageMeaning
                )
            }
            _saveAndClose.postValue(isSuccess)
        }
    }
}