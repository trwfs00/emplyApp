package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emplyapp.databinding.ActivityApplicationsChooseBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Activity_applications_choose : AppCompatActivity(), applications_chooseAdapter.onItemClickListener {
    private lateinit var binding : ActivityApplicationsChooseBinding
    private lateinit var recyclerView: RecyclerView

    val ActivityChooseList = ArrayList<ActivityChooseClass>()

    val createApplicationData = ApplicationAPI.create()

    var KEY_JOB_ID : String? = null
    var KEY_JOB_NAME : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplicationsChooseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent: Intent = getIntent()
        KEY_JOB_ID = intent.getStringExtra("KEY_JOB_ID")
        KEY_JOB_NAME = intent.getStringExtra("KEY_JOB_NAME")

        binding.txtTitle2.setText(KEY_JOB_NAME)

        //Link to Recyclerview
        recyclerView = binding.recyclerViewApp
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onClick(position: Int) {
        TODO("Not yet implemented")
    }
}
