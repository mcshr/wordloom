package com.mcshr.wordloom.data.mappers

import com.mcshr.wordloom.data.entities.LanguageDbModel
import com.mcshr.wordloom.domain.entities.Language


fun Language.toDBModel(): LanguageDbModel {
    return LanguageDbModel(
        id = id,
        name = name,
        code = code
    )
}

fun LanguageDbModel.toDomainEntity(): Language {
    return Language(
        id = id,
        name = name,
        code = code
    )
}

fun List<Language>.toLanguageListDbModel(): List<LanguageDbModel> {
    return map {
        it.toDBModel()
    }
}
