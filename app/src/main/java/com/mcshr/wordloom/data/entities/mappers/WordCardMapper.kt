package com.mcshr.wordloom.data.entities.mappers

import com.mcshr.wordloom.data.entities.CardDbModel
import com.mcshr.wordloom.data.entities.WordDbModel
import com.mcshr.wordloom.data.entities.tuples.DictionaryWithCardsRelation
import com.mcshr.wordloom.data.entities.tuples.WordCardRelation
import com.mcshr.wordloom.domain.entities.WordCard


fun WordCardRelation.toDomainEntity(): WordCard {
    return WordCard(
        wordText = translations.first().wordOriginal.word.wordText,
        wordTranslations = translations.map { it.wordTranslation.word.wordText },
        languageOriginal = translations.first().wordOriginal.language.toDomainEntity(),
        languageTranslation = translations.first().wordTranslation.language.toDomainEntity(),
        partOfSpeech = translations.first().wordOriginal.word.partOfSpeechId.toString(), //TODO
        status = card.status,
        reviewCount = card.reviewsCount,
        nextReviewTime = card.nextRevDate,
        imagePath = card.imagePath,
        id = card.id
    )
}

fun DictionaryWithCardsRelation.toWordCardListDomain(): List<WordCard> {
    return wordCardList.map { it.toDomainEntity() }
}

fun WordCard.toWordDomain(): WordDbModel {
    return WordDbModel(
        id = 0,
        wordText = wordText,
        languageId = languageOriginal.id,
        partOfSpeechId = 0 //TODO pos
    )
}

fun WordCard.toCardDomain(wordId: Long): CardDbModel {
    return CardDbModel(
        id = id,
        status = status,
        reviewsCount = reviewCount,
        nextRevDate = nextReviewTime,
        imagePath = imagePath,
        wordId = wordId
    )
}

fun WordCard.toTranslationsList(): List<WordDbModel> {
    return wordTranslations.map {
        WordDbModel(
            id = 0,
            wordText = it,
            languageId = languageTranslation.id,
            partOfSpeechId = 0 //TODO pos
        )
    }
}


