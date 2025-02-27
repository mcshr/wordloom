package com.mcshr.wordloom.presentation.editWordScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.data.repository.WordCardRepositoryImpl
import com.mcshr.wordloom.domain.interactors.wordCard.CreateWordCardUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.SaveWordCardToDictionaryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditWordViewModel(application: Application) : AndroidViewModel(application) {
    private val wordCardRepository = WordCardRepositoryImpl(application)

    private val createWordCardUseCase = CreateWordCardUseCase(wordCardRepository)
    private val saveWordCardToDictionaryUseCase = SaveWordCardToDictionaryUseCase(wordCardRepository)


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

    fun createWordCardInDictionary(wordText: String, wordMeaningList: List<String>, dictId:Long?) {
        viewModelScope.launch {
            val isSuccess = withContext(Dispatchers.IO) {
                val cardId = createWordCardUseCase(
                    word = wordText,
                    translations = wordMeaningList,
                    partOfSpeech = null,
                    imagePath = null
                )
                saveWordCardToDictionaryUseCase(dictId, cardId)
            }
            _saveAndClose.postValue(isSuccess)
        }
    }

}