package com.example.aksharadeepa.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aksharadeepa.R
import com.example.aksharadeepa.data.Chapter
import com.example.aksharadeepa.data.Topic

class SyllabusAdapter(
    private val onTopicChecked: (Topic, Boolean) -> Unit,
    private val onChapterClicked: (Chapter) -> Unit
) : ListAdapter<SyllabusItem, RecyclerView.ViewHolder>(SyllabusDiffCallback()) {

    companion object {
        private const val TYPE_CHAPTER = 0
        private const val TYPE_TOPIC = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is SyllabusItem.ChapterItem) TYPE_CHAPTER else TYPE_TOPIC
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_CHAPTER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chapter, parent, false)
            ChapterViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
            TopicViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is ChapterViewHolder && item is SyllabusItem.ChapterItem) {
            holder.bind(item.chapter)
        } else if (holder is TopicViewHolder && item is SyllabusItem.TopicItem) {
            holder.bind(item.topic)
        }
    }

    inner class ChapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.chapterName)
        private val quizBtn: View = itemView.findViewById(R.id.takeQuizBtn)

        fun bind(chapter: Chapter) {
            name.text = chapter.name
            quizBtn.setOnClickListener { onChapterClicked(chapter) }
        }
    }

    inner class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.topicName)
        private val checkBox: CheckBox = itemView.findViewById(R.id.topicCheckBox)

        fun bind(topic: Topic) {
            name.text = topic.name
            checkBox.isChecked = topic.isCompleted
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                onTopicChecked(topic, isChecked)
            }
        }
    }
}

sealed class SyllabusItem {
    data class ChapterItem(val chapter: Chapter) : SyllabusItem()
    data class TopicItem(val topic: Topic) : SyllabusItem()
}

class SyllabusDiffCallback : DiffUtil.ItemCallback<SyllabusItem>() {
    override fun areItemsTheSame(oldItem: SyllabusItem, newItem: SyllabusItem): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: SyllabusItem, newItem: SyllabusItem): Boolean {
        return oldItem == newItem
    }
}
