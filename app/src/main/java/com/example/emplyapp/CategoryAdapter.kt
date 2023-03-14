package com.example.emplyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(
    var CategoryList: List<CategoryClass>,
    var mListener: onItemClickListener
    ) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item_layout_for_categoryadapter, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.titleCategory.text = CategoryList[position].category_name

        holder.itemView.setOnClickListener {
            mListener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return CategoryList.size
    }

    class CategoryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val titleCategory : TextView = itemView.findViewById(R.id.txtCategory)
    }

    interface onItemClickListener {
        fun onClick(position: Int)
    }
}