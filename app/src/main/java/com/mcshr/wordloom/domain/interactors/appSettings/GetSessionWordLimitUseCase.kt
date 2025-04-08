package com.mcshr.wordloom.domain.interactors.appSettings

import com.mcshr.wordloom.domain.repository.AppSettingsRepository

class GetSessionWordLimitUseCase(
    private val repository: AppSettingsRepository
) {
    operator fun invoke():Int{
        return repository.getSessionWordLimit()
    }
}