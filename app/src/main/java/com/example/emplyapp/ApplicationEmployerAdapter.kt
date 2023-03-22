package com.example.emplyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.emplyapp.databinding.ApplicationEmployerItemLayoutBinding
import com.example.emplyapp.databinding.ApplicationItemLayoutBinding

class ApplicationEmployerAdapter(private val appemList: List<ApplicationEmployerClass>, private val context: Context) : RecyclerView.Adapter<ApplicationEmployerAdapter.ViewHolder>() {

    class ViewHolder(view: View, val binding: ApplicationEmployerItemLayoutBinding) :
        RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationEmployerAdapter.ViewHolder {
        val binding = ApplicationEmployerItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ApplicationEmployerAdapter.ViewHolder, position: Int) {
        val binding = holder.binding
        binding.jobEmName.text = appemList!![position].job_name
        binding.jobEmInc.text = appemList!![position].company_name
        Glide.with(context).load(appemList[position].logo).into(binding.imgEmJob)
        binding.jobEmCount.text = "${appemList!![position].applicationCount} apply"
    }

    override fun getItemCount(): Int {
        return appemList!!.size
    }
}