package com.example.aksharadeepa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aksharadeepa.R
import com.example.aksharadeepa.databinding.FragmentHomeBinding
import com.example.aksharadeepa.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Add fade-in animation to the content
        val fadeIn = AnimationUtils.loadAnimation(requireContext(), android.R.anim.fade_in)
        binding.homeContentLayout.startAnimation(fadeIn)
        
        setupSubjectList()
        setupGapAreas()
        setupDailyGoal()
        
        viewModel.progressPercentage.observe(viewLifecycleOwner) { progress ->
            binding.overallProgressBar.setProgress(progress, true)
            binding.progressStatus.text = "$progress% of Syllabus Completed"
        }
    }

    private fun setupSubjectList() {
        val adapter = SubjectAdapter { subject ->
            val action = HomeFragmentDirections.actionHomeFragmentToSubjectFragment(subject.id)
            findNavController().navigate(action)
        }
        binding.subjectsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.subjectsRecyclerView.adapter = adapter
        
        viewModel.allSubjects.observe(viewLifecycleOwner) { subjects ->
            adapter.submitList(subjects)
        }
    }

    private fun setupGapAreas() {
        val adapter = GapAreaAdapter { chapter ->
            val action = HomeFragmentDirections.actionHomeFragmentToSubjectFragment(chapter.subjectId)
            findNavController().navigate(action)
        }
        binding.gapAreasRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.gapAreasRecyclerView.adapter = adapter
        
        viewModel.gapChapters.observe(viewLifecycleOwner) { chapters ->
            adapter.submitList(chapters.map { GapChapter(it, 40.0) })
        }
    }

    private fun setupDailyGoal() {
        viewModel.getDailyGoal().observe(viewLifecycleOwner) { goal ->
            // If goal is null, we show the default 0/1 state
            val completedCount = goal?.completedTopics ?: 0
            val targetCount = goal?.targetTopics ?: 1
            val isCompleted = completedCount >= targetCount
            
            binding.dailyGoalProgress.text = "$completedCount/$targetCount"
            
            // Update Blue Tick Status with Animation
            if (isCompleted) {
                binding.goalStatusIcon.setImageResource(R.drawable.ic_check_circle_blue)
                binding.goalStatusIcon.imageTintList = android.content.res.ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.blue_tick)
                )
            } else {
                binding.goalStatusIcon.setImageResource(android.R.drawable.checkbox_off_background)
                binding.goalStatusIcon.imageTintList = android.content.res.ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.white)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
