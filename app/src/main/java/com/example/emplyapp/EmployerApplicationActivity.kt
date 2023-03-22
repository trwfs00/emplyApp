package com.example.emplyapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emplyapp.databinding.ActivityEmployerApplicationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EmployerApplicationActivity : AppCompatActivity(), ApplicationEmployerAdapter.onItemClickListener {
    private lateinit var bindingEmployer: ActivityEmployerApplicationBinding
    private lateinit var recyclerView: RecyclerView
    lateinit var session: SessionManager

    var appemList = arrayListOf<ApplicationEmployerClass>()
    private val applicationCount = 0

    val createApplicationData = ApplicationAPI.create()

    var KEY_JOB_ID : String? = null
    var KEY_JOB_NAME : String? = null
    var KEY_USERNAME : String? = null
    var KEY_EMPLOYER_ID : String? = null

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

        var intent: Intent = getIntent()
        KEY_EMPLOYER_ID = intent.getStringExtra("KEY_EMPLOYER_ID")

        session = SessionManager(applicationContext)
        KEY_USERNAME = session.pref.getString(SessionManager.KEY_USERNAME, null).toString()

        //Link to Recyclerview
        recyclerView = bindingEmployer.recyclerViewAppEm
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        callApplicationEmData()
    }

    fun callApplicationEmData() {
        appemList.clear()

        val employer_id = KEY_EMPLOYER_ID.toString().toInt() // ตั้งค่า employerId ตามที่ต้องการเช็ค
        if (employer_id > 0) {
            createApplicationData.getApplicationEmployer(
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
                                    it.job_id,
                                    it.job_name,
                                    it.company_name,
                                    it.logo,
                                    it.status,
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
        KEY_JOB_ID = appemList[position].job_id.toString()
        KEY_JOB_NAME = appemList[position].job_name
        var intent: Intent = Intent(applicationContext, Activity_applications_choose::class.java)
        intent.putExtra("KEY_JOB_ID",KEY_JOB_ID)
        intent.putExtra("KEY_JOB_NAME",KEY_JOB_NAME)
        startActivity(intent)
    }
}
