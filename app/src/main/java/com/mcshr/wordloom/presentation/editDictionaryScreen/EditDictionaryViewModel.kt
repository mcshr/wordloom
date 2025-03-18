package com.mcshr.wordloom.presentation.editDictionaryScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.data.repository.DictionaryRepositoryImpl
import com.mcshr.wordloom.data.repository.LanguageRepositoryImpl
import com.mcshr.wordloom.domain.entities.Language
import com.mcshr.wordloom.domain.interactors.dictionary.CreateDictionaryUseCase
import com.mcshr.wordloom.domain.interactors.language.GetAllLanguagesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditDictionaryViewModel(application: Application) : AndroidViewModel(application) {
    private val dictRepository = DictionaryRepositoryImpl(application)
    private val langRepository = LanguageRepositoryImpl(application)
    private val createDictionaryUseCase = CreateDictionaryUseCase(dictRepository)
    private val getAllLanguagesUseCase = GetAllLanguagesUseCase(langRepository)

    private val _saveAndClose = MutableLiveData<Boolean>()
    val saveAndClose: LiveData<Boolean>
        get() = _saveAndClose

    val allLanguages = getAllLanguagesUseCase()

    fun createDictionary(name: String, languageWord: Language, languageMeaning: Language) {
        viewModelScope.launch {
            val isSuccess = withContext(Dispatchers.IO) {
                createDictionaryUseCase(
                    name,
                    null,
                    null,
                    languageWord,
                    languageMeaning
                )
            }
            _saveAndClose.postValue(isSuccess)
        }
    }
}