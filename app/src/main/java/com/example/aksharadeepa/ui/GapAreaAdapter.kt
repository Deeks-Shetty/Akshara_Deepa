package com.example.aksharadeepa.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aksharadeepa.data.Chapter
import com.example.aksharadeepa.databinding.ItemGapAreaBinding

data class GapChapter(
    val chapter: Chapter,
    val averageScore: Double
)

class GapAreaAdapter(private val onRetryClick: (Chapter) -> Unit) :
    ListAdapter<GapChapter, GapAreaAdapter.GapViewHolder>(GapDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GapViewHolder {
        val binding = ItemGapAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GapViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GapViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GapViewHolder(private val binding: ItemGapAreaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GapChapter) {
            binding.gapChapterName.text = item.chapter.name
            binding.gapScoreText.text = "Last Score: ${String.format("%.1f", item.averageScore)}%"
            binding.retryButton.setOnClickListener { onRetryClick(item.chapter) }
        }
    }

    class GapDiffCallback : DiffUtil.ItemCallback<GapChapter>() {
        override fun areItemsTheSame(oldItem: GapChapter, newItem: GapChapter): Boolean = oldItem.chapter.id == newItem.chapter.id
        override fun areContentsTheSame(oldItem: GapChapter, newItem: GapChapter): Boolean = oldItem == newItem
    }
}
