package com.mcshr.wordloom.presentation.learningScreen

import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mcshr.wordloom.databinding.ItemCardBinding

class CardViewHolder(val binding: ItemCardBinding): RecyclerView.ViewHolder(binding.root){
    val controller = CardFlipController(binding)
    init{
        binding.root.setOnClickListener {
            controller.flip()
        }
//        controller.onFlipStart = {
//            if (adapterPosition != RecyclerView.NO_POSITION && adapterPosition!= 0){
//                hideFrontSideContent()
//            }
//        }
//        controller.onFlipEnd = {
//            if(adapterPosition!= RecyclerView.NO_POSITION){
//                showFrontSideContent()
//            }
//        }


    }
//    fun cancelFlip(){
//        controller.cancelFlip()
//    }
    fun showFrontSideContent(){
        binding.frontSide.children.forEach {
            it.isVisible = true
        }
    }
    fun hideFrontSideContent(){
        binding.frontSide.children.forEach {
            it.isVisible = false
        }
    }
}