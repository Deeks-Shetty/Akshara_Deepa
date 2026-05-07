package com.example.aksharadeepa.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aksharadeepa.data.Chapter
import com.example.aksharadeepa.databinding.ItemChapterCardBinding

class ChapterAdapter(private val onQuizClick: (Chapter) -> Unit) :
    ListAdapter<Chapter, ChapterAdapter.ChapterViewHolder>(ChapterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val binding = ItemChapterCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ChapterViewHolder(private val binding: ItemChapterCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chapter: Chapter) {
            binding.chapterName.text = chapter.name
            binding.chapterQuizButton.setOnClickListener { onQuizClick(chapter) }
            // Progress logic can be added here
        }
    }

    class ChapterDiffCallback : DiffUtil.ItemCallback<Chapter>() {
        override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean = oldItem == newItem
    }
}
