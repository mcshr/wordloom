package com.mcshr.wordloom.data.mappers

import com.mcshr.wordloom.data.entities.DictionaryDbModel
import com.mcshr.wordloom.data.entities.tuples.DictionaryRelation
import com.mcshr.wordloom.data.entities.tuples.DictionaryWithStatsTuple
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.entities.DictionaryWithStats


fun Dictionary.toDBModel(): DictionaryDbModel {
    return DictionaryDbModel(
        id = id,
        name = name,
        description = description,
        imagePath = imagePath,
        isSelected = isSelected,
        creationDateTime = creationDateTime,
        languageIdOriginal = languageOriginal.id,
        languageIdTranslation = languageTranslation.id
    )
}

fun DictionaryRelation.toDomainEntity(): Dictionary {
    return Dictionary(
        id = dictionary.id,
        name = dictionary.name,
        description = dictionary.description,
        imagePath = dictionary.imagePath,
        isSelected = dictionary.isSelected,
        creationDateTime = dictionary.creationDateTime,
        languageOriginal = languageOriginal.toDomainEntity(),
        languageTranslation = languageTranslation.toDomainEntity()
    )
}

fun List<DictionaryRelation>.toDictionaryListDomain(): List<Dictionary> {
    return map { dictionaryDbModel ->
        dictionaryDbModel.toDomainEntity()
    }
}

fun DictionaryWithStatsTuple.toDomainEntity(): DictionaryWithStats {
    return DictionaryWithStats(
        dictionary = dictionary.toDomainEntity(),
        totalCountCards = total,
        unknownCountCards = unknown,
        knownCountCards = known,
        readyToLearnCountCards = readyToLearn,
        learningCountCards = learning,
        learnedCountCards = learned,
        learningProgress = progress
    )
}

fun List<DictionaryWithStatsTuple>.toDictionaryWithStatsListDomain(): List<DictionaryWithStats> {
    return map { it.toDomainEntity() }
}
