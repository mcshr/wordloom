package com.mcshr.wordloom.data.mappers

import com.mcshr.wordloom.data.entities.CardDbModel
import com.mcshr.wordloom.data.entities.WordDbModel
import com.mcshr.wordloom.data.entities.tuples.DictionaryWithCardsRelation
import com.mcshr.wordloom.data.entities.tuples.WordCardRelation
import com.mcshr.wordloom.domain.entities.PartOfSpeech
import com.mcshr.wordloom.domain.entities.UsageExample
import com.mcshr.wordloom.domain.entities.WordCard


fun WordCardRelation.toDomainEntity(): WordCard {
    return WordCard(
        wordText = translations.first().wordOriginal.word.wordText,
        wordTranslations = translations.map { it.wordTranslation.word.wordText },
        languageOriginal = translations.first().wordOriginal.language.toDomainEntity(),
        languageTranslation = translations.first().wordTranslation.language.toDomainEntity(),
        partOfSpeech = PartOfSpeech.fromCode(translations.first().wordOriginal.word.partOfSpeechCode),
        status = card.status,
        reviewCount = card.reviewsCount,
        nextReviewTime = card.nextRevDate,
        imagePath = card.imagePath,
        id = card.id,
        usageExamples = usageExamples.map {
            UsageExample(text = it.exampleText,
            translation = it.exampleTextTranslation
        ) }
    )
}

fun DictionaryWithCardsRelation.toWordCardListDomain(): List<WordCard> {
    return wordCardList.map { it.toDomainEntity() }
}

fun WordCard.toWordDBModel(): WordDbModel {
    return WordDbModel(
        id = 0,
        wordText = wordText,
        languageId = languageOriginal.id,
        partOfSpeechCode = partOfSpeech.code
    )
}

fun WordCard.toCardDBModel(): CardDbModel {
    return CardDbModel(
        id = id,
        status = status,
        reviewsCount = reviewCount,
        nextRevDate = nextReviewTime,
        imagePath = imagePath
    )
}

fun WordCard.toTranslationsList(): List<WordDbModel> {
    return wordTranslations.map {
        WordDbModel(
            id = 0,
            wordText = it,
            languageId = languageTranslation.id,
            partOfSpeechCode = partOfSpeech.code
        )
    }
}


