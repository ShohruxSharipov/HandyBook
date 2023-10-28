package com.example.handybook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.handybook.databinding.RomanItemBinding
import com.example.handybook.databinding.SearchItemBinding
import com.example.handybook.model.Book

class SearchAdapter(val list: List<Book>, val onClickBook: RomanAdapter.OnClickBook) :
    RecyclerView.Adapter<SearchAdapter.SearchHolder>() {

    class SearchHolder(binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val book = binding.book
        val image = binding.booksImage
        val title = binding.bookName
        val author = binding.bookauthor
        val rating = binding.bookRayting
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        return SearchHolder(
            SearchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val book = list[position]
        holder.image.load(book.image)
        holder.title.text = book.name
        holder.author.text = book.author
        holder.rating.text = book.reyting.toDouble().toString()
        holder.book.setOnClickListener {
            onClickBook.onClickRoman(book)
        }
    }
}