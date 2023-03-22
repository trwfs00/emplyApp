package com.example.emplyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.emplyapp.databinding.ApplicationItemLayoutBinding

class ApplicationAdapter(val applicationList: ArrayList<ApplicationClass>, val context: Context) :
    RecyclerView.Adapter<ApplicationAdapter.ViewHolder>() {

    class ViewHolder(view: View, val binding: ApplicationItemLayoutBinding) :
        RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ApplicationItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding.root, binding)
    }

    override fun getItemCount(): Int {
        return applicationList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding

        binding.jobName.text = "${applicationList!![position].job_name}"
        binding.jobInc.text = "${applicationList!![position].company_name}"
        Glide.with(context).load(applicationList[position].logo).into(binding.imgJob)

    }
}
