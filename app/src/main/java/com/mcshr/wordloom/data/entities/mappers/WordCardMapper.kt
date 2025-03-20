package com.mcshr.wordloom.data.entities.mappers

import com.mcshr.wordloom.data.entities.CardDbModel
import com.mcshr.wordloom.data.entities.WordDbModel
import com.mcshr.wordloom.data.entities.tuples.DictionaryWithCardsRelation
import com.mcshr.wordloom.data.entities.tuples.WordCardRelation
import com.mcshr.wordloom.domain.entities.WordCard

object WordCardMapper {
    fun mapWordCardRelationToWordCard(wordCardDB: WordCardRelation): WordCard {
        return WordCard(
            wordText = wordCardDB.translations.first().wordOriginal.word.wordText,
            wordTranslations = wordCardDB.translations.map { it.wordTranslation.word.wordText },
            languageOriginal = LanguageMapper.mapToDomainEntity(wordCardDB.translations.first().wordOriginal.language),
            languageTranslation = LanguageMapper.mapToDomainEntity(wordCardDB.translations.first().wordTranslation.language),
            partOfSpeech = wordCardDB.translations.first().wordOriginal.word.partOfSpeechId.toString(), //TODO
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
            languageId = wordCard.languageOriginal.id,
            partOfSpeechId = 0 //TODO pos
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
                languageId = wordCard.languageTranslation.id,
                partOfSpeechId = 0 //TODO pos
            )
        }
    }


}