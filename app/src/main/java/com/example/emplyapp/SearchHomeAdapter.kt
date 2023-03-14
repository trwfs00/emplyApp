package com.example.emplyapp

import SearchHomeClass
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchHomeAdapter(var SearchHomeList : List<SearchHomeClass>): RecyclerView.Adapter<SearchHomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHomeAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_item_layout, parent, false)
        return SearchHomeAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleCountry.text = SearchHomeList[position].job_name

//        holder.itemView.setOnClickListener {
//            mListener.onClick(position)
//        }
    }

    override fun getItemCount(): Int {
        return SearchHomeList.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val titleCountry : TextView = itemView.findViewById(R.id.JobName)
    }

    interface onItemClickListener {
        fun onClick(position: Int)
    }
}