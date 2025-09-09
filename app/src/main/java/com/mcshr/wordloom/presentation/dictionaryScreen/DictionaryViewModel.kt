package com.mcshr.wordloom.presentation.dictionaryScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.interactors.appSettings.SaveSelectedDictionaryForWordUseCase
import com.mcshr.wordloom.domain.interactors.dictionary.GetDictionaryUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.DeleteWordCardUseCase
import com.mcshr.wordloom.domain.interactors.wordCard.GetWordCardListByDictIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDictionaryUseCase: GetDictionaryUseCase,
    private val saveSelectedDictionaryForWordUseCase: SaveSelectedDictionaryForWordUseCase,
    getWordCardListByDictIdUseCase: GetWordCardListByDictIdUseCase,
    private val deleteWordCardUseCase: DeleteWordCardUseCase
) : ViewModel() {

    private val dictionaryId: Long = savedStateHandle["dictionaryId"]
        ?: throw RuntimeException("dictionary id is null on DictionaryViewModel")

    private val _dictionary = MutableLiveData<Dictionary>()
    val dictionaryLiveData: LiveData<Dictionary>
        get() = _dictionary


    val wordList = getWordCardListByDictIdUseCase(dictionaryId)


    init {
        viewModelScope.launch {
            val dictionary = withContext(Dispatchers.IO) {
                getDictionaryUseCase(dictionaryId)
            }
            _dictionary.value = dictionary
        }

    }
    fun selectDictionaryToAddWord(){
        saveSelectedDictionaryForWordUseCase.invoke(dictionaryId)
    }
    fun deleteWordCard(wordCard: WordCard){
        viewModelScope.launch(Dispatchers.IO) {
            deleteWordCardUseCase(wordCard, dictionaryId)
        }
    }
}