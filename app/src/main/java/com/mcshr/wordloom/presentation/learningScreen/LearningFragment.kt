package com.mcshr.wordloom.presentation.learningScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mcshr.wordloom.databinding.FragmentLearningBinding
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import com.yuyakaido.android.cardstackview.SwipeableMethod
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LearningFragment : Fragment() {

    private var _binding: FragmentLearningBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("Fragment Learning binding is null")

    private val viewModel: LearningViewModel by viewModels()
    private val cardAdapter = CardStackAdapter()

    private lateinit var cardStackLayoutManager: CardStackLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLearningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardStackListener = object : CardStackListener {
            override fun onCardSwiped(direction: Direction?) {
                direction?:return
                viewModel.onCardSwipe(direction)
            }
            override fun onCardDragging(direction: Direction?, ratio: Float) {}
            override fun onCardRewound() {}
            override fun onCardCanceled() {}
            override fun onCardAppeared(view: View?, position: Int) {
                val viewHolder = binding.cardStack.findViewHolderForAdapterPosition(
                    cardStackLayoutManager.topPosition
                ) as? CardViewHolder
                viewHolder?.fadeInContent()
            }
            override fun onCardDisappeared(view: View?, position: Int) {}
        }
        cardStackLayoutManager = CardStackLayoutManager(
            context,
            cardStackListener
        ).apply {
            setVisibleCount(3)
            setStackFrom(StackFrom.Top)
            setTranslationInterval(10f)
            setMaxDegree(25f)
            setScaleInterval(0.95f)
            setCanScrollVertical(false)
        }
        //Disable default element animations in RV
        binding.cardStack.itemAnimator = null

        binding.cardStack.layoutManager = cardStackLayoutManager
        var isFirstTime = true
        viewModel.learningSet.observe(viewLifecycleOwner) {
            val topPosition = viewModel.position
            cardAdapter.cardList = it
            cardAdapter.topPosition = topPosition
            if(isFirstTime) {
                cardStackLayoutManager.scrollToPosition(topPosition)
                isFirstTime = false
            }
        }

        cardAdapter.toggleCardStackSwipe = { canSwipe ->
            val swipeableMethod =
                if (canSwipe) SwipeableMethod.AutomaticAndManual else SwipeableMethod.None
            cardStackLayoutManager.setSwipeableMethod(swipeableMethod)
        }

        binding.cardStack.adapter = cardAdapter

        binding.btnSwipeLeft.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(200)
                .build()
            cardStackLayoutManager.setSwipeAnimationSetting(setting)
            binding.cardStack.swipe()
        }
        binding.btnSwipeRight.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(200)
                .build()
            cardStackLayoutManager.setSwipeAnimationSetting(setting)
            binding.cardStack.swipe()
        }
        viewModel.readyToClose.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                delay(400)
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

}