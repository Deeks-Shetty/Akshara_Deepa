package com.example.aksharadeepa.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aksharadeepa.data.Topic
import com.example.aksharadeepa.databinding.ItemTopicBinding

class TopicAdapter(
    private val onTopicChecked: (Topic, Boolean) -> Unit,
    private val onTopicClicked: (Topic) -> Unit
) : ListAdapter<Topic, TopicAdapter.TopicViewHolder>(TopicDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val binding = ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TopicViewHolder(private val binding: ItemTopicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic) {
            binding.topicName.text = topic.name
            binding.topicCheckBox.setOnCheckedChangeListener(null)
            binding.topicCheckBox.isChecked = topic.isCompleted
            
            binding.topicCheckBox.setOnCheckedChangeListener { _, isChecked ->
                onTopicChecked(topic, isChecked)
            }
            
            binding.root.setOnClickListener {
                onTopicClicked(topic)
            }
        }
    }

    class TopicDiffCallback : DiffUtil.ItemCallback<Topic>() {
        override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean = oldItem == newItem
    }
}
