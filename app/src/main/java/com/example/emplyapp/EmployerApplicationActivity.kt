package com.example.emplyapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emplyapp.databinding.ActivityEmployerApplicationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EmployerApplicationActivity : AppCompatActivity(), ApplicationEmployerAdapter.onItemClickListener {
    private lateinit var bindingEmployer: ActivityEmployerApplicationBinding
    var appemList = arrayListOf<ApplicationEmployerClass>()
    private val applicationCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingEmployer = ActivityEmployerApplicationBinding.inflate(layoutInflater)
        setContentView(bindingEmployer.root)

        bindingEmployer.bottomNav.selectedItemId = R.id.application
        bindingEmployer.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    var i: Intent = Intent(applicationContext, EmployerHomeActivity::class.java)
                    startActivity(i)
                    finish()
                    it.isChecked = true
                }
                R.id.application -> {
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

        //Link to Recyclerview
        bindingEmployer.recyclerViewAppEm.adapter =
            ApplicationEmployerAdapter(this.appemList, applicationContext, this@EmployerApplicationActivity)
        bindingEmployer.recyclerViewAppEm.layoutManager = LinearLayoutManager(applicationContext)
        bindingEmployer.recyclerViewAppEm.addItemDecoration(
            DividerItemDecoration(
                bindingEmployer.recyclerViewAppEm.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onResume() {
        super.onResume()
        callApplicationEmData()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun callApplicationEmData() {
        appemList.clear()
        val serv: ApplicationAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApplicationAPI::class.java)

        val employer_id = 2 // ตั้งค่า employerId ตามที่ต้องการเช็ค
        if (employer_id > 0) {
            serv.getApplicationEmployer(
                employer_id = employer_id
            )
                .enqueue(object : Callback<List<ApplicationEmployerClass>> {
                    override fun onResponse(
                        call: Call<List<ApplicationEmployerClass>>,
                        response: Response<List<ApplicationEmployerClass>>
                    ) {
                        response.body()?.forEach {
                            appemList.add(
                                ApplicationEmployerClass(
                                    it.job_name,
                                    it.company_name,
                                    it.logo,
                                    it.logo.toString(),
                                    it.applicationCount
                                )
                            )
                        }
                        //set data to recyclerview
                        bindingEmployer.recyclerViewAppEm.adapter =
                            ApplicationEmployerAdapter(appemList, applicationContext, this@EmployerApplicationActivity)
                    }

                    override fun onFailure(
                        call: Call<List<ApplicationEmployerClass>>,
                        t: Throwable
                    ) {
                        Toast.makeText(
                            applicationContext,
                            "Error onFailure" + t.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        } else {
            Toast.makeText(applicationContext, "Invalid employer ID", Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(position: Int) {
        var intent: Intent = Intent(applicationContext, ActivityJobDetail::class.java)
        startActivity(intent)
    }
}
