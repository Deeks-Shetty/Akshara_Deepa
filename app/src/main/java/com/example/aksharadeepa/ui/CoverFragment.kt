package com.example.aksharadeepa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aksharadeepa.R
import com.example.aksharadeepa.databinding.FragmentCoverBinding

class CoverFragment : Fragment() {

    private var _binding: FragmentCoverBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hide action bar for cover page
        (activity as? AppCompatActivity)?.supportActionBar?.hide()

        binding.btnGetStarted.setOnClickListener {
            findNavController().navigate(R.id.action_coverFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Show action bar again when leaving cover page
        (activity as? AppCompatActivity)?.supportActionBar?.show()
        _binding = null
    }
}
