package com.example.aksharadeepa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aksharadeepa.databinding.FragmentGapAnalysisBinding
import com.example.aksharadeepa.viewmodel.MainViewModel

class GapAnalysisFragment : Fragment() {

    private var _binding: FragmentGapAnalysisBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGapAnalysisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GapAreaAdapter { chapter ->
            // Use SafeArgs or bundle to navigate to quiz instructions for retry
            val bundle = Bundle().apply {
                putInt("chapterId", chapter.id)
            }
            findNavController().navigate(com.example.aksharadeepa.R.id.action_gapAnalysisFragment_to_quizInstructionFragment, bundle)
        }

        binding.gapRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.gapRecyclerView.adapter = adapter

        // Observe gap chapters (Focus Areas)
        viewModel.gapChapters.observe(viewLifecycleOwner) { chapters ->
            if (chapters.isNullOrEmpty()) {
                binding.emptyStateText.visibility = View.VISIBLE
                binding.gapRecyclerView.visibility = View.GONE
            } else {
                binding.emptyStateText.visibility = View.GONE
                binding.gapRecyclerView.visibility = View.VISIBLE
                // Map chapters to GapChapter objects (assuming 40% as a placeholder score for display)
                adapter.submitList(chapters.map { GapChapter(it, 40.0) })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
