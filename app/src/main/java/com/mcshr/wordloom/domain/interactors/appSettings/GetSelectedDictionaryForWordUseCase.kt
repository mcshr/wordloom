package com.mcshr.wordloom.domain.interactors.appSettings

import com.mcshr.wordloom.domain.repository.AppSettingsRepository
import com.mcshr.wordloom.domain.repository.DictionaryRepository
import javax.inject.Inject

class GetSelectedDictionaryForWordUseCase
@Inject constructor(
    private val settingsRepository: AppSettingsRepository,
    private val dictionaryRepository: DictionaryRepository
) {
    suspend operator fun invoke(): Long? {
        return settingsRepository.getLastSelectedDictionaryIdForWord()
            ?: dictionaryRepository.getLastCreatedDictionary()?.id
    }
}

