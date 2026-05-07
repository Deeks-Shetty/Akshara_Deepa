package com.example.aksharadeepa.ui

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.work.WorkManager
import com.example.aksharadeepa.AksharaDeepaApp
import com.example.aksharadeepa.databinding.FragmentSettingsBinding
import com.example.aksharadeepa.viewmodel.MainViewModel
import java.util.*

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutResetProgress.setOnClickListener {
            showResetConfirmationDialog()
        }
        
        binding.switchReminders.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                (requireActivity().application as AksharaDeepaApp).scheduleDailyReminder()
                Toast.makeText(context, "Daily reminders enabled", Toast.LENGTH_SHORT).show()
            } else {
                WorkManager.getInstance(requireContext()).cancelAllWorkByTag("daily_reminder")
                Toast.makeText(context, "Reminders disabled", Toast.LENGTH_SHORT).show()
            }
        }

        binding.layoutReminderTime.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
            val timeString = formatTime(selectedHour, selectedMinute)
            binding.tvReminderTime.text = timeString
            
            // Save time and reschedule
            // In a full implementation, we'd save this to SharedPreferences
            (requireActivity().application as AksharaDeepaApp).scheduleDailyReminder(selectedHour, selectedMinute)
            Toast.makeText(context, "Reminder set for $timeString", Toast.LENGTH_SHORT).show()
        }, hour, minute, false).show()
    }

    private fun formatTime(hour: Int, minute: Int): String {
        val amPm = if (hour < 12) "AM" else "PM"
        val h = if (hour == 0 || hour == 12) 12 else hour % 12
        return String.format(Locale.getDefault(), "%02d:%02d %s", h, minute, amPm)
    }

    private fun showResetConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Reset Progress")
            .setMessage("Are you sure you want to reset all your progress? This cannot be undone.")
            .setPositiveButton("Reset") { _, _ ->
                viewModel.clearProgress()
                Toast.makeText(context, "All progress has been reset", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
