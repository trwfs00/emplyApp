package com.example.emplyapp

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.emplyapp.databinding.JobRecentItemLayoutBinding
import java.sql.DriverManager
import java.time.Duration
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import java.time.*
import java.time.temporal.ChronoUnit


class JobRecentAdapter(val jobrecentlist:ArrayList<JobRecent>?, val context: Context):
    RecyclerView.Adapter<JobRecentAdapter.ViewHolder>(){
    inner class ViewHolder(view: View, val bindingJobRecent: JobRecentItemLayoutBinding):
        RecyclerView.ViewHolder(view){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = JobRecentItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding.root,binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.bindingJobRecent

        binding.jobName?.text = jobrecentlist!![position].job_name
        binding.jobInc?.text = jobrecentlist!![position].company_name
        binding.jobCountry?.text = jobrecentlist!![position].nicename
        binding.jobType?.text = jobrecentlist!![position].type
        binding.jobTime?.text = diffForHuman(jobrecentlist!![position].created_at)
        Glide.with(context).load(jobrecentlist[position].logo).into(binding.imgJob)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun diffForHuman(created_at: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val createdAtDateTime = LocalDateTime.parse(created_at, formatter).atOffset(ZoneOffset.UTC).withOffsetSameInstant(ZoneId.of("Asia/Bangkok").rules.getOffset(Instant.now())).toLocalDateTime()
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

    override fun getItemCount(): Int {
        return  jobrecentlist!!.size
    }
}


//    @RequiresApi(Build.VERSION_CODES.O)
//    fun diffForHuman(timestamp: LocalDateTime): String {
//        val duration = Duration.between(timestamp, LocalDateTime.now())
//        return when {
//            duration.toMinutes() < 1 -> "just now"
//            duration.toMinutes() < 60 -> "${duration.toMinutes()} minutes ago"
//            duration.toHours() < 24 -> "${duration.toHours()} hours ago"
//            duration.toDays() < 7 -> "${duration.toDays()} days ago"
//            else -> "on ${timestamp.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))}"
//        }
//    }

//    fun main() {
//        val url = "jdbc:mysql://localhost/mydatabase"
//        val props = Properties()
//        props.setProperty("user", "myuser")
//        props.setProperty("password", "mypassword")
//        val conn = DriverManager.getConnection(url, props)
//
//        val sql = "SELECT created_at FROM mytable WHERE id = ?"
//        val stmt = conn.prepareStatement(sql)
//        stmt.setInt(1, 123) // Replace 123 with the ID of the record you want to retrieve
//        val rs = stmt.executeQuery()
//
//        if (rs.next()) {
//            val timestamp = rs.getTimestamp("created_at").toLocalDateTime()
//            val diff = diffForHuman(timestamp)
//            println(diff)
//        } else {
//            println("Record not found")
//        }
//
//        rs.close()
//        stmt.close()
//        conn.close()
//    }