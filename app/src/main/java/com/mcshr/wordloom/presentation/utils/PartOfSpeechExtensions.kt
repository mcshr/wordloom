package com.mcshr.wordloom.presentation.utils

import com.mcshr.wordloom.R
import com.mcshr.wordloom.domain.entities.PartOfSpeech

fun PartOfSpeech.toResId():Int{
    return when(this){
        PartOfSpeech.EMPTY -> R.string.pos_empty
        PartOfSpeech.NOUN -> R.string.pos_noun
        PartOfSpeech.VERB -> R.string.pos_verb
        PartOfSpeech.ADJECTIVE -> R.string.pos_adjective
        PartOfSpeech.ADVERB -> R.string.pos_adverb
        PartOfSpeech.OTHER -> R.string.pos_other
    }
}