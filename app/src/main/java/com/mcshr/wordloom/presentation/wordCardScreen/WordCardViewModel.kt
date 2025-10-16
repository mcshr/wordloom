package com.mcshr.wordloom.presentation.wordCardScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.domain.entities.WordCard
import com.mcshr.wordloom.domain.interactors.wordCard.GetWordCardByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordCardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getWordCardByIdUseCase: GetWordCardByIdUseCase
) : ViewModel() {
    val wordCardId = savedStateHandle.get<Long>("wordCardId") ?: -1

    private val _wordCard = MutableLiveData<WordCard>()
    val wordCard: LiveData<WordCard>
        get() = _wordCard

    init {
        getWordCard()
    }

    private fun getWordCard(){
        viewModelScope.launch(Dispatchers.IO) {
            _wordCard.postValue(getWordCardByIdUseCase(wordCardId))
        }
    }

}