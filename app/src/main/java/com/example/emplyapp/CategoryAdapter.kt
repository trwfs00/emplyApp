package com.example.emplyapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.emplyapp.databinding.CategoryItemLayoutBinding
import kotlin.collections.ArrayList

class CategoryAdapter(val categoryList: ArrayList<CategoryClass>, val context: Context) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(view: View, val bindingCat: CategoryItemLayoutBinding) :
        RecyclerView.ViewHolder(view) {
        init {
            bindingCat.jobCategory.setOnClickListener {
                val context: Context = view.context
                val intent = Intent(context, HomeActivity::class.java)
                intent.putExtra("category_name", categoryList[adapterPosition].category_name)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = CategoryItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.bindingCat
        binding.jobCategory.text = "${categoryList!![position].category_name}"
    }

    override fun getItemCount(): Int {
        return categoryList!!.size
    }
}