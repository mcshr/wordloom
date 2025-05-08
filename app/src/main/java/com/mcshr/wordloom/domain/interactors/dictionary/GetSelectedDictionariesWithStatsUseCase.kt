package com.mcshr.wordloom.domain.interactors.dictionary

import androidx.lifecycle.LiveData
import com.mcshr.wordloom.domain.entities.DictionaryWithStats
import com.mcshr.wordloom.domain.repository.DictionaryRepository
import javax.inject.Inject

class GetSelectedDictionariesWithStatsUseCase @Inject constructor(
    private val repository: DictionaryRepository
) {
    operator fun invoke():LiveData<List<DictionaryWithStats>>{
        return repository.getSelectedDictionariesWithStats()
    }
}