package org.tensorflow.lite.examples.classification.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.classification.databinding.RecognitionItemBinding
import org.tensorflow.lite.examples.classification.viewmodel.Recognition

class RecognitionAdapter(private val ctx: Context) :
    ListAdapter<Recognition, RecognitionViewHolder>(RecognitionDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecognitionViewHolder {
        val inflater = LayoutInflater.from(ctx)
        val binding = RecognitionItemBinding.inflate(inflater, parent, false)
        return RecognitionViewHolder(binding)
    }
    override fun onBindViewHolder(holder: RecognitionViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    private class RecognitionDiffUtil : DiffUtil.ItemCallback<Recognition>() {
        override fun areItemsTheSame(oldItem: Recognition, newItem: Recognition): Boolean {
            return oldItem.label == newItem.label
        }

        override fun areContentsTheSame(oldItem: Recognition, newItem: Recognition): Boolean {
            return oldItem.confidence == newItem.confidence
        }
    }


}

class RecognitionViewHolder(private val binding: RecognitionItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindTo(recognition: Recognition) {
        binding.recognitionItem = recognition
        binding.executePendingBindings()
    }
}