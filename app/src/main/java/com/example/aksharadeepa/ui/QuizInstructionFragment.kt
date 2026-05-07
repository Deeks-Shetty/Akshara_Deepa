package com.example.aksharadeepa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aksharadeepa.databinding.FragmentQuizInstructionBinding

class QuizInstructionFragment : Fragment() {

    private var _binding: FragmentQuizInstructionBinding? = null
    private val binding get() = _binding!!
    private val args: QuizInstructionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizInstructionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startQuizNowButton.setOnClickListener {
            val action = QuizInstructionFragmentDirections.actionQuizInstructionFragmentToQuizFragment(args.chapterId)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
