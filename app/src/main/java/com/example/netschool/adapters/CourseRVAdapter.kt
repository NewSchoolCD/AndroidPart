package com.example.netschool.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.netschool.databinding.CardTutorBinding
import com.example.netschool.model.Subject

class CourseRVAdapter() : RecyclerView.Adapter<CourseRVAdapter.CourseViewHolder>() {


    inner class CourseViewHolder(val binding: CardTutorBinding) :
        RecyclerView.ViewHolder(binding.root)


    val diffCallback = object : DiffUtil.ItemCallback<Subject>() {
        override fun areItemsTheSame(oldItem: Subject, newItem: Subject) =
            oldItem.label == newItem.label

        override fun areContentsTheSame(oldItem: Subject, newItem: Subject) =
            oldItem == newItem
    }

    val diffList = AsyncListDiffer(this, diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        return CourseViewHolder(
            CardTutorBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return diffList.currentList.size
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val tutorCard = diffList.currentList[position]
        holder.binding.apply {

        }
    }

}