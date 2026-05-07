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
import com.example.aksharadeepa.databinding.FragmentSubjectBinding
import com.example.aksharadeepa.viewmodel.MainViewModel

class SubjectFragment : Fragment() {

    private var _binding: FragmentSubjectBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private val args: SubjectFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ChapterAdapter { chapter ->
            val action = SubjectFragmentDirections.actionSubjectFragmentToChapterFragment(chapter.id)
            findNavController().navigate(action)
        }

        binding.chaptersRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.chaptersRecyclerView.adapter = adapter

        viewModel.allSubjects.observe(viewLifecycleOwner) { subjects ->
            val subject = subjects.find { it.id == args.subjectId }
            binding.subjectTitle.text = subject?.name ?: "Subject"
        }

        viewModel.getChapters(args.subjectId).observe(viewLifecycleOwner) { chapters ->
            adapter.submitList(chapters)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
