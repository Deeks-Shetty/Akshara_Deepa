package com.example.aksharadeepa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aksharadeepa.databinding.FragmentQuizResultBinding

class QuizResultFragment : Fragment() {

    private var _binding: FragmentQuizResultBinding? = null
    private val binding get() = _binding!!
    private val args: QuizResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val score = args.score
        val total = args.total
        val percentage = if (total > 0) (score * 100) / total else 0

        binding.resultScoreText.text = "$score/$total"
        binding.resultPercentageText.text = "$percentage%"
        binding.correctCount.text = score.toString()
        binding.wrongCount.text = (total - score).toString()

        binding.retryQuizButton.setOnClickListener {
            val action = QuizResultFragmentDirections.actionQuizResultFragmentToQuizInstructionFragment(args.chapterId)
            findNavController().navigate(action)
        }

        binding.reviewAnswersButton.setOnClickListener {
            val action = QuizResultFragmentDirections.actionQuizResultFragmentToReviewFragment(args.chapterId)
            findNavController().navigate(action)
        }

        binding.backHomeButton.setOnClickListener {
            findNavController().navigate(QuizResultFragmentDirections.actionQuizResultFragmentToHomeFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
