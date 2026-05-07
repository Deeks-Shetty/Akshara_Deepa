package com.example.aksharadeepa.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aksharadeepa.data.Subject
import com.example.aksharadeepa.databinding.ItemSubjectCardBinding

class SubjectAdapter(private val onClick: (Subject) -> Unit) :
    ListAdapter<Subject, SubjectAdapter.SubjectViewHolder>(SubjectDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding = ItemSubjectCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subject = getItem(position)
        holder.bind(subject)
    }

    inner class SubjectViewHolder(private val binding: ItemSubjectCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(subject: Subject) {
            binding.subjectName.text = subject.name
            // In a real app, calculate actual progress from ViewModel or data source
            binding.subjectProgressText.text = "Tap to view chapters"
            binding.root.setOnClickListener { onClick(subject) }
        }
    }

    class SubjectDiffCallback : DiffUtil.ItemCallback<Subject>() {
        override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean = oldItem == newItem
    }
}
