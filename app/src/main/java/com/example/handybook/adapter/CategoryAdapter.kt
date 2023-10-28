package com.example.handybook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.handybook.databinding.CategoryButtonBinding
import com.example.handybook.model.Category

class CategoryAdapter(val list:List<Category>, val onClickCategory: onClickCategorya ):RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {
    class CategoryHolder(binding: CategoryButtonBinding): RecyclerView.ViewHolder(binding.root){
        val name = binding.category
        val button = binding.categorybutton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(CategoryButtonBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.name.text = list[position].type_name
        holder.button.setOnClickListener {
            onClickCategory.onClickCategory(list[position])
        }
    }
    interface onClickCategorya{
        fun onClickCategory(category: Category)
    }
}