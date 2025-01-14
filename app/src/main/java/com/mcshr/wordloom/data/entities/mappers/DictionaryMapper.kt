package com.mcshr.wordloom.data.entities.mappers

import com.mcshr.wordloom.data.entities.DictionaryDbModel
import com.mcshr.wordloom.domain.entities.Dictionary

class DictionaryMapper {
    fun mapToDatabaseModel(dictionary:Dictionary):DictionaryDbModel
    {
        return DictionaryDbModel(
            id = dictionary.id,
            name = dictionary.name,
            description = dictionary.description,
            imagePath = dictionary.imagePath,
            isSelected = dictionary.isSelected,
            creationDateTime = dictionary.creationDateTime
        )
    }
    fun mapToDomainEntity(dictionary:DictionaryDbModel):Dictionary
    {
        return Dictionary(
            id = dictionary.id,
            name = dictionary.name,
            description = dictionary.description,
            imagePath = dictionary.imagePath,
            isSelected = dictionary.isSelected,
            creationDateTime = dictionary.creationDateTime
        )
    }

}