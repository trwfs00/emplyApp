package com.example.emplyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.emplyapp.databinding.JobItemLayoutBinding
import com.example.emplyapp.databinding.JobRecentItemLayoutBinding

class HomeAdapter(val JobRecentlist:ArrayList<JobRecent>?, val context: Context):
    RecyclerView.Adapter<HomeAdapter.ViewHolder>(){
    inner class ViewHolder(view: View, val binding: JobRecentItemLayoutBinding):
        RecyclerView.ViewHolder(view){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = JobRecentItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding.root,binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding

        binding.jobName?.text = "${JobRecentlist!![position].job_name}"
        binding.jobInc?.text = "${JobRecentlist!![position].company_name}"
        binding.jobCountry?.text = "${JobRecentlist!![position].country}"
        binding.jobType?.text = "${JobRecentlist!![position].type}"
        binding.jobTime?.text = "${JobRecentlist!![position].created_at}"
        Glide.with(context).load(JobRecentlist[position].logo).into(binding.imgJob)
    }

    override fun getItemCount(): Int {
        return  JobRecentlist!!.size
    }
}