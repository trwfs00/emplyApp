package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emplyapp.databinding.ActivityHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var session: SessionManager

    var  JobRecentlist = arrayListOf<JobRecent>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.bottomNav.selectedItemId = R.id.home
        binding.JobRecentRycyclerView.adapter = HomeAdapter(this.JobRecentlist,applicationContext)
        binding.JobRecentRycyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.JobRecentRycyclerView.addItemDecoration(
            DividerItemDecoration(binding.JobRecentRycyclerView.getContext(),
                DividerItemDecoration.VERTICAL)
        )


        session = SessionManager(applicationContext)
        // Read data from the preferences
        val username: String? = session.pref.getString(SessionManager.KEY_USERNAME, null)
        binding.txtUsername.text = username

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    it.isChecked = true
                }
                R.id.application -> {
                    var i: Intent = Intent(applicationContext, ApplicationActivity::class.java)
                    startActivity(i)
                    finish()
                    it.isChecked = true
                }
                R.id.profile -> {
                    var i: Intent = Intent(applicationContext, ProfileActivity::class.java)
                    startActivity(i)
                    finish()
                    it.isChecked = true
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        callJobRecentData()
    }

    private fun callJobRecentData(){
        JobRecentlist.clear()
        val serv : JobRecentAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JobRecentAPI ::class.java)
        serv.getJobsRecent()
            .enqueue(object : Callback<List<JobRecent>> {
                override fun onResponse(
                    call: Call<List<JobRecent>>,
                    response: Response<List<JobRecent>>,
                ) {
                    response.body()?.forEach {
                        JobRecentlist.add(JobRecent(it.job_name,it.company_name,it.nicename,it.type,it.logo,it.created_at))
                    }
                    binding.JobRecentRycyclerView.adapter = HomeAdapter(JobRecentlist,applicationContext)
                }

                override fun onFailure(call: Call<List<JobRecent>>, t: Throwable) {
                    Toast.makeText(applicationContext,"Error onFailure" + t.message,Toast.LENGTH_LONG).show()
                }

            })
    }
}