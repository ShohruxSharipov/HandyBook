package com.example.handybook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.handybook.databinding.CommentItemBinding
import com.example.handybook.model.Comment

class CommentAdapter(val list:List<Comment>):RecyclerView.Adapter<CommentAdapter.CommentHolder>() {
    class CommentHolder(binding:CommentItemBinding) : RecyclerView.ViewHolder(binding.root){
        val username = binding.username
        val text = binding.text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        return CommentHolder(CommentItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        val comment = list[position]
        holder.username.text = comment.username
        holder.text.text = comment.text
    }
}