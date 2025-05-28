package com.mcshr.wordloom.presentation.learningScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mcshr.wordloom.databinding.FragmentLearningBinding
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import dagger.hilt.android.AndroidEntryPoint

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
                if (direction == null )
                    return
                val position = cardStackLayoutManager.topPosition - 1
                val wordCard = cardAdapter.cardList[position]
                viewModel.onCardSwipe(wordCard, direction)
            }
            override fun onCardDragging(direction: Direction?, ratio: Float) {}
            override fun onCardRewound() {}
            override fun onCardCanceled() {}
            override fun onCardAppeared(view: View?, position: Int) {            }
            override fun onCardDisappeared(view: View?, position: Int) {}

        } as CardStackListener
        cardStackLayoutManager = CardStackLayoutManager(
            context,
            cardStackListener
        ).apply {
            setVisibleCount(2)
        }
        binding.cardStack.itemAnimator = null
        binding.cardStack.layoutManager = cardStackLayoutManager

        viewModel.learningSet.observe(viewLifecycleOwner) {
            cardAdapter.cardList = it
        }
        binding.cardStack.adapter = cardAdapter


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().popBackStack()
        }
    }
}