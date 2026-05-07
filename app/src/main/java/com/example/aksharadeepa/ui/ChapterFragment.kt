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
import com.example.aksharadeepa.databinding.FragmentChapterBinding
import com.example.aksharadeepa.viewmodel.MainViewModel

class ChapterFragment : Fragment() {

    private var _binding: FragmentChapterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private val args: ChapterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChapterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TopicAdapter(
            onTopicChecked = { topic, isChecked ->
                viewModel.updateTopic(topic.copy(isCompleted = isChecked))
            },
            onTopicClicked = { topic ->
                val action = ChapterFragmentDirections.actionChapterFragmentToQuizInstructionFragment(
                    chapterId = args.chapterId,
                    topicId = topic.id
                )
                findNavController().navigate(action)
            }
        )

        binding.topicsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.topicsRecyclerView.adapter = adapter

        viewModel.getTopics(args.chapterId).observe(viewLifecycleOwner) { topics ->
            adapter.submitList(topics)
            val completed = topics.count { it.isCompleted }
            val total = topics.size
            val progress = if (total > 0) (completed * 100) / total else 0
            binding.chapterProgressText.text = "Progress: $progress%"
            binding.chapterProgressBar.progress = progress
        }

        binding.startQuizButton.setOnClickListener {
            val action = ChapterFragmentDirections.actionChapterFragmentToQuizInstructionFragment(
                chapterId = args.chapterId,
                topicId = -1
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
