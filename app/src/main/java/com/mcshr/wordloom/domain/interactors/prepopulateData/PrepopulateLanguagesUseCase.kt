package com.mcshr.wordloom.domain.interactors.prepopulateData

import android.util.Log
import com.mcshr.wordloom.domain.entities.Language
import com.mcshr.wordloom.domain.repository.LanguageRepository
import com.mcshr.wordloom.domain.repository.PrepopulateDataRepository
import javax.inject.Inject

class PrepopulateLanguagesUseCase @Inject constructor(
    private val prepopulateRepo: PrepopulateDataRepository,
    private val languageRepo: LanguageRepository
) {
    suspend operator fun invoke() {

        val languages = listOf(
            Language("en", "English", 1),
            Language("ru", "Russian", 2),
            Language("ua", "Ukrainian", 3),
        )

        if (languageRepo.getLanguagesCount()>=languages.size) {
            Log.d("DATA","Languages already prepopulated")
            return
        }

        prepopulateRepo.prepopulateLanguages(languages)
    }
}