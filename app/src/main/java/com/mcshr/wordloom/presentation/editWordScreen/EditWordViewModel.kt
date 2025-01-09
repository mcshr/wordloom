package com.mcshr.wordloom.presentation.editWordScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditWordViewModel: ViewModel() {
    private val _meaningList: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    val meaningList: LiveData<List<String>>
        get() = _meaningList

    fun addMeaning(meaning: String):Boolean{
        if(meaning.isNotBlank() && _meaningList.value?.contains(meaning) == false ) {
            _meaningList.value = _meaningList.value?.plus(meaning)
            return true
        }
        return false
    }

    fun deleteMeaning(meaning :String):String{
        _meaningList.value =  _meaningList.value?.filterNot { it == meaning }
        return meaning
    }

}