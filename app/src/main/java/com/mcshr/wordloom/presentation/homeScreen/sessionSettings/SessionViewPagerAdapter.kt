package com.mcshr.wordloom.presentation.homeScreen.sessionSettings

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SessionViewPagerAdapter(fragment:Fragment):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return PAGES_COUNT
    }
    override fun createFragment(position: Int): Fragment {
        return when(position) {
                0 -> SessionModeLearningFragment.newInstance()
                1 -> SessionModeSwipeFragment.newInstance()
                else -> throw IllegalArgumentException("Invalid page position")
        }

    }
    companion object{
        private val PAGES_COUNT = 2
    }
}