package com.example.aksharadeepa.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aksharadeepa.data.QuizResultWithChapter
import com.example.aksharadeepa.databinding.ItemQuizResultBinding
import java.text.SimpleDateFormat
import java.util.*

class QuizResultAdapter : ListAdapter<QuizResultWithChapter, QuizResultAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemQuizResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemQuizResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: QuizResultWithChapter) {
            binding.chapterNameText.text = result.chapterName
            binding.chapterNameText.setTextColor(Color.WHITE) // Ensure it's white
            binding.scoreText.text = "${result.score}/${result.totalQuestions}"
            val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            binding.dateText.text = sdf.format(Date(result.timestamp))
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<QuizResultWithChapter>() {
        override fun areItemsTheSame(oldItem: QuizResultWithChapter, newItem: QuizResultWithChapter): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: QuizResultWithChapter, newItem: QuizResultWithChapter): Boolean = oldItem == newItem
    }
}
