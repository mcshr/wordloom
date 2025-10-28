package com.mcshr.wordloom.presentation.learningScreen

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.mcshr.wordloom.databinding.ItemCardBinding


class CardViewHolder(val binding: ItemCardBinding): RecyclerView.ViewHolder(binding.root){
    val controller = CardFlipController(binding)
    init{
        binding.root.setOnClickListener {
            controller.flip()
        }
        val gestureDetector = GestureDetector(
            binding.root.context,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    controller.flip()
                    return true
                }

                override fun onDown(e: MotionEvent): Boolean {
                    return true
                }
            })
        @SuppressLint("ClickableViewAccessibility")
        binding.rvExamples.setOnTouchListener { v, event ->
            v.performClick()
            gestureDetector.onTouchEvent(event)
            false
        }
    }

    fun hideFrontSideContent(){
        binding.frontSide.children.forEach {
            it.alpha = 0f
        }
        controller.cancelFlip()
    }
    fun fadeInContent() {
        binding.frontSide.children.forEach {
            it.alpha = 0f
        }
        binding.frontSide.children.forEach {
            it.animate()
                .alpha(1f)
                .setDuration(150)
                .start()
        }
    }

}