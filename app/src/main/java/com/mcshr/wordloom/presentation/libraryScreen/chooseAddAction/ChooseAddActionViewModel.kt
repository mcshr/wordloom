package com.mcshr.wordloom.presentation.libraryScreen.chooseAddAction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcshr.wordloom.domain.interactors.dictionary.CheckIfAnyDictionaryExistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseAddActionViewModel @Inject constructor(
    private val checkIfAnyDictionaryExists: CheckIfAnyDictionaryExistsUseCase
) : ViewModel() {
    private val _isAnyDictExists = MutableLiveData<Boolean>()
    val isAnyDictionaryExists: LiveData<Boolean>
        get() = _isAnyDictExists

    init {
        viewModelScope.launch {
            _isAnyDictExists.value = checkIfAnyDictionaryExists.invoke()
        }
    }
}