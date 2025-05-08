package com.mcshr.wordloom.domain.interactors.appSettings

import com.mcshr.wordloom.domain.repository.AppSettingsRepository
import javax.inject.Inject

class SaveSessionWordLimitUseCase @Inject constructor(
    private val repository: AppSettingsRepository
) {
    operator fun invoke(wordLimit: Int) {
        repository.saveSessionWordLimit(wordLimit)
    }
}