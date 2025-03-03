package com.mcshr.wordloom.data.entities.mappers

import com.mcshr.wordloom.data.entities.LanguageDbModel
import com.mcshr.wordloom.domain.entities.Language

object LanguageMapper {
    fun mapToDatabaseModel(language: Language):LanguageDbModel{
        return LanguageDbModel(
            id = 0,
            name = language.name,
            code = language.code
        )
    }
    fun mapEntityListToDbModelList(languages: List<Language>):List<LanguageDbModel>{
        return languages.map{
            mapToDatabaseModel(it)
        }
    }
}