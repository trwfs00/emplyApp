package com.example.emplyapp

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.emplyapp.databinding.ChooseItemLayoutBinding
import java.time.*
import java.time.format.DateTimeFormatter

class applications_chooseAdapter(
    private val ActivityChooseList: List<ActivityChooseClass>,
    private val context: Context,
    private val mListener: Activity_applications_choose
) : RecyclerView.Adapter<applications_chooseAdapter.ViewHolder>() {

    class ViewHolder(view: View, val binding: ChooseItemLayoutBinding) :
        RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChooseItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding.root, binding)
    }

    override fun getItemCount(): Int {
        return ActivityChooseList!!.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding

        binding.JobName.text = ActivityChooseList!![position].fullName
        binding.jobDate.text = diffForHuman(ActivityChooseList!![position].created_at)
        Glide.with(context).load(ActivityChooseList!![position].picture_jobseek).into(binding.imgJobSeek)

        holder.itemView.setOnClickListener {
            mListener.onClick(position)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun diffForHuman(created_at: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val createdAtDateTime = LocalDateTime.parse(created_at, formatter).atOffset(ZoneOffset.UTC).withOffsetSameInstant(
            ZoneId.of("Asia/Bangkok").rules.getOffset(Instant.now())).toLocalDateTime()
        val currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Bangkok"))
        val duration = Duration.between(createdAtDateTime, currentDateTime)

        return when {
            duration.toMinutes() < 1 -> "just now"
            duration.toMinutes() < 60 -> "${duration.toMinutes()}m"
            duration.toHours() < 24 -> "${duration.toHours()}hrs"
            duration.toDays() < 7 -> "${duration.toDays()}d"
            else -> "on ${createdAtDateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))}"
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun main() {
        val created_at = "2023-03-14T20:35:29.000Z"
        val diff = diffForHuman(created_at)
        println(diff) // Output: "just now" (if the current time is close to the input timestamp)
    }

    interface onItemClickListener {
        fun onClick(position: Int)
    }
}