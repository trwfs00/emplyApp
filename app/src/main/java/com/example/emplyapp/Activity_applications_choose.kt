package com.example.emplyapp

import android.annotation.SuppressLint
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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        recyclerView = binding.recyclerViewApply
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)



    }

    override fun onResume() {
        super.onResume()
        callApplyJobssekerData()
    }

    override fun onClick(position: Int) {
        callApplyJobssekerData()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun callApplyJobssekerData(){
        ActivityChooseList.clear()
        val serv : ApplicationAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApplicationAPI ::class.java)

        KEY_JOB_ID?.toInt()?.let {
            serv.getApplicationsApply(
                job_id = it
            )

                .enqueue(object : Callback<List<ActivityChooseClass>> {
                    override fun onResponse(
                        call: Call<List<ActivityChooseClass>>,
                        response: Response<List<ActivityChooseClass>>
                    ) {
                        response.body()?.forEach {
                            ActivityChooseList.add(ActivityChooseClass(
                                it.job_name,
                                it.fullName,
                                it.created_at,
                                it.picture_jobseek,
                            ))
                        }
                        //set data to recyclerview
                        binding.recyclerViewApply.adapter = applications_chooseAdapter(ActivityChooseList,applicationContext,this@Activity_applications_choose)
                    }


                    override fun onFailure(call: Call<List<ActivityChooseClass>>, t: Throwable) {
                        Toast.makeText(applicationContext,"Error onFailure" + t.message, Toast.LENGTH_LONG).show()
                    }
                })
        }
    }
}

private fun <T> Call<T>.enqueue(callback: Callback<List<T>>) {

}


