package com.example.emplyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.emplyapp.databinding.ActivitySearchBinding
import com.example.emplyapp.databinding.SearchItemLayoutBinding

class SearchAdapter(
    private val searchList: List<SearchClass>,
    private val mListener: SearchActivity,
    private val context: Context
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(itemView : View, val binding: SearchItemLayoutBinding)
        : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val binding = holder.binding
        val Search = searchList[position]

        binding.JobName.text = Search.job_name
        binding.jobInc.text = Search.company_name
        binding.jobLocation.text = Search.state
        binding.jobSalaryFrom.text = Search.salaryFrom.toString()
        binding.jobSalaryTo.text = Search.salaryTo.toString()
        binding.jobType.text = Search.type
        Glide.with(context).load(Search.logo).into(binding.imgJob)

        holder.itemView.setOnClickListener {
            mListener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return searchList!!.size
    }

    interface onItemClickListener {
        fun onClick(position: Int)
    }
}