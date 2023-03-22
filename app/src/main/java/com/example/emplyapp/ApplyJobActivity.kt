package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.emplyapp.databinding.ActivityApplyJobBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplyJobActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplyJobBinding
    lateinit var session: SessionManager

    private var SearchList = ArrayList<SearchClass>()
    val searchClient = SearchAPI.create()
    val createUserData = UserAPI.create()

    var PDF_PATH: String? = null

    var KEY_JOB_ID: Int? = null

    var KEY_JOBSEEK_ID: Int? = null
    var KEY_ROLE: Int? = null
    var KEY_USERNAME: String? = null
    var KEY_FULLNAME: String? = null
    var KEY_EMAIL: String? = null
    var KEY_PHONE: Int? = null
    var KEY_BIRTHDAY: Int? = null
    var KEY_NICKNAME: String? = null
    var KEY_GENDER: String? = null
    var KEY_COUNTRY_ID: Int? = null
    var KEY_PICTURE: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(applicationContext)
        KEY_USERNAME = session.pref.getString(SessionManager.KEY_USERNAME, null).toString()
        getUserData(KEY_USERNAME.toString())

        binding.btnUploadResume.setOnClickListener {
            showInputDialog()
        }
        binding.btnSubmit.setOnClickListener {
            addJobs()
        }

    }

    override fun onResume() {
        super.onResume()
        callJobData()

    }

    fun callJobData() {
        var jobName = intent.getStringExtra("jobName")
        SearchList.clear()
        searchClient.getSearch(jobName.toString())
            .enqueue(object : Callback<List<SearchClass>> {
                override fun onResponse(
                    call: Call<List<SearchClass>>,
                    response: Response<List<SearchClass>>
                ) {
                    response.body()?.forEach {
                        SearchList.add(
                            SearchClass(
                                it.job_id,
                                it.job_name,
                                it.company_name,
                                it.country_name,
                                it.nicename,
                                it.state,
                                it.salaryFrom,
                                it.salaryTo,
                                it.type,
                                it.description,
                                it.minimumQualification,
                                it.benefit,
                                it.category_name,
                                it.logo
                            )
                        )
                    }
                    KEY_JOB_ID = SearchList[0].job_id
                }

                override fun onFailure(call: Call<List<SearchClass>>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }

    private fun showInputDialog() {
        val dialogBuilder = AlertDialog.Builder(this)

        val editText = EditText(this)
        dialogBuilder.setTitle("PDF URL")
            .setView(editText)
            .setPositiveButton("OK") { dialogInterface, i ->
                val inputText = editText.text.toString()
                PDF_PATH = inputText
                Toast.makeText(applicationContext, "You entered: $inputText", Toast.LENGTH_SHORT)
                    .show()
            }
            .setNegativeButton("Cancel") { dialogInterface, i ->
                dialogInterface.cancel()
            }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun getUserData(msg: String) {
        createUserData.fetchJobSeekData(
            username = msg
        ).enqueue(object : Callback<JobseekerClass> {
            override fun onResponse(
                call: Call<JobseekerClass>,
                response: Response<JobseekerClass>
            ) {
                if (response.isSuccessful) {
                    KEY_JOBSEEK_ID = response.body()?.jobseeker_id

                    binding.edtFullName.setText(response.body()?.fullName)
                    binding.edtEmail.setText(response.body()?.email)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Failure on calling user data...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<JobseekerClass>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed on " + t.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun addJobs() {
        getUserData(KEY_USERNAME.toString())
        createUserData.insertApply(
            job_id = KEY_JOB_ID.toString().toInt(),
            resume = PDF_PATH.toString(),
            motivation = binding.edtMotivationLetter.toString(),
            jobseeker_id = KEY_JOBSEEK_ID.toString().toInt()
        ).enqueue(object : Callback<ApplyClass> {
            override fun onResponse(call: Call<ApplyClass>, response: Response<ApplyClass>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "Successfully Apply Job", Toast.LENGTH_LONG)
                        .show()
                    var i: Intent = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Failed on Apply job..", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<ApplyClass>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Error onFailure: " + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

}