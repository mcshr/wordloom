package com.mcshr.wordloom.presentation.editWordScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.interactors.wordCard.CreateWordCardUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.SaveWordCardToDictionaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditWordViewModel @Inject constructor(
    private val createWordCardUseCase :CreateWordCardUseCase,
    private val saveWordCardToDictionaryUseCase : SaveWordCardToDictionaryUseCase
) : ViewModel() {

    private val _meaningList: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    val meaningList: LiveData<List<String>>
        get() = _meaningList

    private val _saveAndClose = MutableLiveData<Boolean>()
    val saveAndClose: LiveData<Boolean>
        get() = _saveAndClose

    fun addMeaning(meaning: String): Boolean {
        if (_meaningList.value?.contains(meaning) == false) {
            _meaningList.value = _meaningList.value?.plus(meaning)
            return true
        }
        return false
    }

    fun deleteMeaning(meaning: String): String {
        _meaningList.value = _meaningList.value?.filterNot { it == meaning }
        return meaning
    }

    fun createWordCardInDictionary(wordText: String, wordMeaningList: List<String>, dict:Dictionary) {
        viewModelScope.launch {
            val isSuccess = withContext(Dispatchers.IO) {
                val cardId = createWordCardUseCase(
                    word = wordText,
                    translations = wordMeaningList,
                    partOfSpeech = null,
                    imagePath = null,
                    languageOriginal = dict.languageOriginal,
                    languageTranslation = dict.languageTranslation
                )
                saveWordCardToDictionaryUseCase(dict.id, cardId)
            }
            _saveAndClose.postValue(isSuccess)
        }
    }

}