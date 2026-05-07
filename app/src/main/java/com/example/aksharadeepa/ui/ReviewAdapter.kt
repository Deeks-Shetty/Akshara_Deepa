package com.example.aksharadeepa.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aksharadeepa.data.Question
import com.example.aksharadeepa.databinding.ItemReviewBinding

data class ReviewItem(
    val question: Question,
    val selectedAnswer: String,
    val isCorrect: Boolean
)

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private var items: List<ReviewItem> = emptyList()

    fun submitList(newItems: List<ReviewItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ReviewViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReviewItem) {
            binding.reviewQuestionText.text = item.question.text
            binding.userAnswerText.text = "Your Answer: ${item.selectedAnswer}"
            binding.correctAnswerText.text = "Correct Answer: ${item.question.correctAnswer}"
            binding.explanationText.text = "Explanation: ${item.question.explanation}"
            
            val color = if (item.isCorrect) 0xFF4CAF50.toInt() else 0xFFFF5252.toInt()
            binding.userAnswerText.setTextColor(color)
        }
    }
}
