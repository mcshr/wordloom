package com.mcshr.wordloom.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mcshr.wordloom.data.entities.mappers.DictionaryMapper
import com.mcshr.wordloom.domain.WordloomRepository
import com.mcshr.wordloom.domain.entities.Dictionary
import com.mcshr.wordloom.domain.entities.WordCard

class WordloomRepositoryImpl(application: Application):WordloomRepository {

    private val database = AppDatabase.getInstance(application)
    private val dao = database.appDao()
    private val dictMapper = DictionaryMapper()


    override fun createWordCard(wordCard: WordCard) {
        dao.insertWordCard(wordCard)
    }

    override fun editWordCard(wordCard: WordCard) {
        TODO("Not yet implemented")
    }

    override fun deleteWordCard(wordCard: WordCard) {
        TODO("Not yet implemented")
    }

    override fun getWordCardById(wordCardId: Int): WordCard {
        TODO("Not yet implemented")
    }

    override fun getWordCardList(): LiveData<List<WordCard>> {
        TODO("Not yet implemented")
    }

    override fun getWordsForReview(currentTime: Long): List<WordCard> {
        TODO("Not yet implemented")
    }

    //Dictionary

    override suspend fun createDictionary(dictionary: Dictionary) {
       dao.createDictionary(dictMapper.mapToDatabaseModel(dictionary))
    }

    override fun editDictionary(dictionary: Dictionary) {
        TODO("Not yet implemented")
    }

    override fun deleteDictionary(dictionary: Dictionary) {
        TODO("Not yet implemented")
    }

    override suspend fun getDictionary(dictionaryId: Long):Dictionary {
       return dictMapper.mapToDomainEntity(
           dao.getDictionaryById(dictionaryId)
       )
    }

    override fun getAllDictionaries(): LiveData<List<Dictionary>> {
        return dao.getAllDictionaries().map {
            list -> dictMapper.mapListToDomainEntityList(list)
        }
    }
}