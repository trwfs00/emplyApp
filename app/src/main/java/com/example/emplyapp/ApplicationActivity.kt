package com.example.emplyapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emplyapp.databinding.ActivityApplicationBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityApplicationBinding
    var applicationList = arrayListOf<ApplicationClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNav.selectedItemId = R.id.application
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    var i: Intent = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(i)
                    finish()
                }
                R.id.application -> {
                    it.isChecked = true
                }
                R.id.profile -> {
                    var i: Intent = Intent(applicationContext, ProfileActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }
            true
        }
        //Link to Recyclerview
        binding.recyclerViewApp.adapter =
            ApplicationAdapter(this.applicationList, applicationContext)
        binding.recyclerViewApp.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerViewApp.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerViewApp.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onResume() {
        super.onResume()
        callApplicationData()
    }



    @SuppressLint("SuspiciousIndentation")
    private fun callApplicationData(){
        applicationList.clear()
        val serv : ApplicationAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApplicationAPI ::class.java)

            serv.getApplications(
                jobseeker_id = 3
            )
                .enqueue(object : Callback<List<ApplicationClass>> {
                    override fun onResponse(
                        call: Call<List<ApplicationClass>>,
                        response: Response<List<ApplicationClass>>
                    ) {
                        response.body()?.forEach {
                            applicationList.add(ApplicationClass(
                                it.job_name,
                                it.company_name,
                                it.logo,
                            ))
                        }
                        //set data to recyclerview
                        binding.recyclerViewApp.adapter = ApplicationAdapter(applicationList,applicationContext)
                    }

                    override fun onFailure(call: Call<List<ApplicationClass>>, t: Throwable) {
                        Toast.makeText(applicationContext,"Error onFailure" + t.message, Toast.LENGTH_LONG).show()
                    }
                })
        }
    }
