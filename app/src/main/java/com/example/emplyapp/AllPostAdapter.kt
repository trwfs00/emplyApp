package com.example.emplyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.emplyapp.databinding.YourPostItemLayoutBinding

class AllPostAdapter(private val allpostList: List<AllPostClass>, private val context: Context) : RecyclerView.Adapter<AllPostAdapter.ViewHolder>() {

    class ViewHolder(view: View, val binding: YourPostItemLayoutBinding) :
        RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllPostAdapter.ViewHolder {
        val binding = YourPostItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: AllPostAdapter.ViewHolder, position: Int) {
        val binding = holder.binding
        binding.JobName.text = allpostList!![position].job_name
        binding.jobInc.text = allpostList!![position].company_name
        binding.jobLocation.text = allpostList!![position].nicename
        binding.jobSalaryFrom.text = allpostList!![position].salaryFrom.toInt().toString()
        binding.jobSalaryTo.text = allpostList!![position].salaryTo.toInt().toString()
        binding.jobType.text = allpostList!![position].type
        Glide.with(context).load(allpostList[position].logo).into(binding.imgJob)

    }

    override fun getItemCount(): Int {
        return allpostList!!.size
    }
}