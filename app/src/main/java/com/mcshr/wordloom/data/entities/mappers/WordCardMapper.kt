package com.mcshr.wordloom.data.entities.mappers

import com.mcshr.wordloom.data.entities.CardDbModel
import com.mcshr.wordloom.data.entities.WordDbModel
import com.mcshr.wordloom.data.entities.tuples.DictionaryWithCardsRelation
import com.mcshr.wordloom.data.entities.tuples.WordCardRelation
import com.mcshr.wordloom.domain.entities.WordCard

object WordCardMapper {
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

    fun mapWordCardToWord(wordCard: WordCard):WordDbModel{
        return WordDbModel(
            id = 0,
            wordText = wordCard.wordText,
            languageId = 0, //TODO
            partOfSpeechId = 0 //^
        )
    }
    fun mapWordCardToCard(wordCard: WordCard, wordId:Long):CardDbModel {
        return CardDbModel(
            id = 0,
            status = wordCard.status,
            reviewsCount = wordCard.reviewCount,
            nextRevDate = wordCard.nextReviewTime,
            imagePath = wordCard.imagePath,
            wordId = wordId
        )
    }
    fun mapWordCardToMeaningList(wordCard: WordCard):List<WordDbModel>{
        return wordCard.wordTranslations.map {
            WordDbModel(
                id = 0,
                wordText = it,
                languageId = 0, //TODO
                partOfSpeechId = 0
            )
        }
    }


}