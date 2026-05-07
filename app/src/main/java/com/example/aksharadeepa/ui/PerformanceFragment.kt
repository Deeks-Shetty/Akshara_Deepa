package com.example.aksharadeepa.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.aksharadeepa.databinding.FragmentPerformanceBinding
import com.example.aksharadeepa.viewmodel.MainViewModel
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class PerformanceFragment : Fragment() {

    private var _binding: FragmentPerformanceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerformanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRadarChart()
    }

    private fun setupRadarChart() {
        viewModel.allSubjects.observe(viewLifecycleOwner) { subjects ->
            val entries = mutableListOf<RadarEntry>()
            val labels = mutableListOf<String>()

            subjects.forEach { subject ->
                labels.add(subject.name)
                // In a real app, calculate actual mastery per subject
                entries.add(RadarEntry((40..90).random().toFloat())) 
            }

            val dataSet = RadarDataSet(entries, "Subject Mastery")
            dataSet.color = Color.parseColor("#6200EE")
            dataSet.fillColor = Color.parseColor("#E91E63")
            dataSet.setDrawFilled(true)
            dataSet.fillAlpha = 180
            dataSet.lineWidth = 2f
            dataSet.valueTextSize = 12f
            dataSet.valueTextColor = Color.BLACK // Changed from WHITE to BLACK as requested

            val data = RadarData(dataSet)
            binding.radarChart.apply {
                this.data = data
                description.isEnabled = false
                xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                xAxis.textColor = Color.BLACK
                xAxis.textSize = 12f
                yAxis.axisMinimum = 0f
                yAxis.axisMaximum = 100f
                yAxis.setDrawLabels(false)
                
                // Refresh chart
                animateXY(1000, 1000)
                invalidate()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
