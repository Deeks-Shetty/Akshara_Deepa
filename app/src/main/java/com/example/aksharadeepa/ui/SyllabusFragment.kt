package com.example.aksharadeepa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aksharadeepa.databinding.FragmentSyllabusBinding
import com.example.aksharadeepa.viewmodel.MainViewModel

class SyllabusFragment : Fragment() {

    private var _binding: FragmentSyllabusBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSyllabusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SyllabusAdapter(
            onTopicChecked = { topic, isChecked ->
                viewModel.updateTopic(topic.copy(isCompleted = isChecked))
            },
            onChapterClicked = { chapter ->
                val action = SyllabusFragmentDirections.actionSyllabusFragmentToQuizInstructionFragment(chapter.id)
                findNavController().navigate(action)
            }
        )

        binding.syllabusRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.syllabusRecyclerView.adapter = adapter

        viewModel.allSubjects.observe(viewLifecycleOwner) { subjects ->
            val items = mutableListOf<SyllabusItem>()
            subjects.forEach { subject ->
                // For simplicity in v1, we just load all chapters/topics or based on selection
                // Here we fetch chapters for the first subject as an example
                viewModel.getChapters(subject.id).observe(viewLifecycleOwner) { chapters ->
                    chapters.forEach { chapter ->
                        items.add(SyllabusItem.ChapterItem(chapter))
                        viewModel.getTopics(chapter.id).observe(viewLifecycleOwner) { topics ->
                            topics.forEach { items.add(SyllabusItem.TopicItem(it)) }
                            adapter.submitList(items.toList())
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
