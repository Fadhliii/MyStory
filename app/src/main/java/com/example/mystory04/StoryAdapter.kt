package com.example.mystory04

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mystory04.Response.ListStoryItem
import com.example.mystory04.databinding.ActivityListStoryBinding

class StoryAdapter(
        private val data: MutableList<ListStoryItem> = mutableListOf(),
) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    //set data from API to Adapter
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data:  List<ListStoryItem>) {
        this.data.clear() //clear
        this.data.addAll(data) //add data after clearing
        notifyDataSetChanged() // and then refresh RV
    }

    class StoryViewHolder(private val v: ActivityListStoryBinding) :
            RecyclerView.ViewHolder(v.root) {
        fun bind(story: ListStoryItem) {
            // bind data to views here
            v.headerImage.load(story.photoUrl) {
                crossfade(true)
            }
            v.title.text = story.name //name
            v.body.text = story.description //desc
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        //inflate like in viewbinding
        return StoryViewHolder(
                ActivityListStoryBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val data = data[position]
        holder.bind(data)
    }

}