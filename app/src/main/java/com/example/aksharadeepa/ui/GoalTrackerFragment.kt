package com.example.aksharadeepa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.aksharadeepa.databinding.FragmentGoalTrackerBinding
import com.example.aksharadeepa.viewmodel.MainViewModel

class GoalTrackerFragment : Fragment() {

    private var _binding: FragmentGoalTrackerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoalTrackerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDailyGoal().observe(viewLifecycleOwner) { goal ->
            goal?.let {
                binding.goalCountText.text = "${it.targetTopics} Topics per Day"
                binding.goalSlider.value = it.targetTopics.toFloat()
                binding.goalProgressBar.max = it.targetTopics
                binding.goalProgressBar.progress = it.completedTopics
                binding.goalStatusText.text = "${it.completedTopics}/${it.targetTopics} topics completed today"
            }
        }

        binding.goalSlider.addOnChangeListener { _, value, _ ->
            binding.goalCountText.text = "${value.toInt()} Topics per Day"
        }

        binding.saveGoalButton.setOnClickListener {
            viewModel.updateDailyGoal(binding.goalSlider.value.toInt())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
