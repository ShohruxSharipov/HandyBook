package com.example.handybook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.handybook.databinding.DarslikItemBinding
import com.example.handybook.model.Book

class DarslikAdapter(val list: List<Book>,val onClickBook: RomanAdapter.OnClickBook):RecyclerView.Adapter<DarslikAdapter.DarslikHolder>() {
    class DarslikHolder(binding:DarslikItemBinding):RecyclerView.ViewHolder(binding.root){
        val image = binding.subjectImg
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DarslikHolder {
        return DarslikHolder(DarslikItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DarslikHolder, position: Int) {
        val book = list[position]
        holder.image.load(book.image)
        holder.image.setOnClickListener {
            onClickBook.onClickRoman(book)
        }
    }
}