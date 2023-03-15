package com.example.emplyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SearchAdapter(
    var SearchList: ArrayList<SearchClass>,
    var mListener: SearchActivity,
    val context: Context
    ) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val jobName : TextView = itemView.findViewById(R.id.JobName)
        val companyName : TextView = itemView.findViewById(R.id.jobInc)
        val jobLocation : TextView = itemView.findViewById(R.id.jobLocation)
        val jobSalaryFrom : TextView = itemView.findViewById(R.id.jobSalaryFrom)
        val jobSalaryTo : TextView = itemView.findViewById(R.id.jobSalaryTo)
        val jobImg : ImageView = itemView.findViewById(R.id.imgJob)
        val jobType : TextView = itemView.findViewById(R.id.jobType)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item_layout, parent, false)
        return SearchAdapter.SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val Search = SearchList[position]

        holder.jobName.text = Search.job_name
        holder.companyName.text = Search.company_name
        holder.jobLocation.text = Search.country_name
        holder.jobSalaryFrom.text = Search.salaryFrom.toString()
        holder.jobSalaryTo.text = Search.salaryTo.toString()
        holder.jobType.text = Search.type
        Glide.with(context).load(Search.logo).into(holder.jobImg)

        holder.itemView.setOnClickListener {
            mListener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return SearchList.size
    }

    interface onItemClickListener {
        fun onClick(position: Int)
    }
}