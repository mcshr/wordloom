package com.mcshr.wordloom.data.entities.mappers

import com.mcshr.wordloom.data.entities.DictionaryDbModel
import com.mcshr.wordloom.data.entities.tuples.DictionaryRelation
import com.mcshr.wordloom.domain.entities.Dictionary

object DictionaryMapper {
    fun mapToDatabaseModel(dictionary:Dictionary):DictionaryDbModel
    {
        return DictionaryDbModel(
            id = dictionary.id,
            name = dictionary.name,
            description = dictionary.description,
            imagePath = dictionary.imagePath,
            isSelected = dictionary.isSelected,
            creationDateTime = dictionary.creationDateTime,
            languageIdOriginal = dictionary.languageOriginal.id,
            languageIdTranslation = dictionary.languageTranslation.id
        )
    }
    fun mapToDomainEntity(dictionary:DictionaryRelation):Dictionary
    {
        return Dictionary(
            id = dictionary.dictionary.id,
            name = dictionary.dictionary.name,
            description = dictionary.dictionary.description,
            imagePath = dictionary.dictionary.imagePath,
            isSelected = dictionary.dictionary.isSelected,
            creationDateTime = dictionary.dictionary.creationDateTime,
            languageOriginal = LanguageMapper.mapToDomainEntity(dictionary.languageOriginal),
            languageTranslation = LanguageMapper.mapToDomainEntity(dictionary.languageTranslation)
        )
    }

    fun mapListToDomainEntityList(list:List<DictionaryRelation>):List<Dictionary>{
        return list.map{
            dictionaryDbModel -> mapToDomainEntity(dictionaryDbModel)
        }
    }
}