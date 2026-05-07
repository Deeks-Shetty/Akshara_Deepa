package com.example.aksharadeepa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aksharadeepa.databinding.FragmentReviewBinding
import com.example.aksharadeepa.viewmodel.MainViewModel

class ReviewFragment : Fragment() {

    private var _binding: FragmentReviewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private val args: ReviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ReviewAdapter()
        binding.reviewRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.reviewRecyclerView.adapter = adapter

        // In a real app, we would fetch questions and user answers.
        // For this demo, we'll fetch questions for the chapter and show mock review data.
        viewModel.getLatestResult(args.chapterId).observe(viewLifecycleOwner) { result ->
            result?.let {
                binding.quizScoreText.text = "Score: ${it.score}/${it.totalQuestions}"
            }
        }

        binding.backToHomeButton.setOnClickListener {
            findNavController().navigate(ReviewFragmentDirections.actionReviewFragmentToHomeFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
