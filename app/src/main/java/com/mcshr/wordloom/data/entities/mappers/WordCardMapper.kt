package com.mcshr.wordloom.data.entities.mappers

import com.mcshr.wordloom.data.entities.tuples.DictionaryWithCardsRelation
import com.mcshr.wordloom.data.entities.tuples.WordCardRelation
import com.mcshr.wordloom.domain.entities.WordCard

class WordCardMapper {
    fun mapWordCardRelationToWordCard(wordCardDB: WordCardRelation): WordCard {
        return WordCard(
            wordText = wordCardDB.translations.first().wordOriginal.wordText,
            wordTranslations = wordCardDB.translations.map { it.wordTranslation.wordText },
            partOfSpeech = wordCardDB.translations.first().wordOriginal.partOfSpeechId.toString(), //TODO
            status = wordCardDB.card.status,
            reviewCount = wordCardDB.card.reviewsCount,
            nextReviewTime = wordCardDB.card.nextRevDate,
            imagePath = wordCardDB.card.imagePath,
            id = wordCardDB.card.id
        )
    }

    fun mapListDBModelToListDomainEntity(dictWithCard: DictionaryWithCardsRelation): List<WordCard> {
        return dictWithCard.wordCardList.map { mapWordCardRelationToWordCard(it) }
    }
}