package com.example.handybook.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.handybook.databinding.LanguageItemBinding
import com.example.handybook.model.Language

class LanguageAdapter(val list: List<Language>, val changeLan: ChangeLan):RecyclerView.Adapter<LanguageAdapter.LanguageHolder>() {
    class LanguageHolder(binding: LanguageItemBinding): RecyclerView.ViewHolder(binding.root){
        val img = binding.img
        val lang = binding.lang
        val language = binding.language
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageHolder {
        return LanguageHolder(LanguageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LanguageHolder, position: Int) {
        holder.img.setBackgroundResource(list[position].flag)
        holder.lang.text = list[position].name
        holder.language.setOnClickListener {
            holder.language.setBackgroundColor(Color.BLUE)
            changeLan.ChangeLanguage(list[position])
        }
    }
    interface ChangeLan{
        fun ChangeLanguage(language: Language)
    }
}