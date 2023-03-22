package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.emplyapp.databinding.ActivityEmployerHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EmployerHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployerHomeBinding
    private lateinit var session: SessionManager
    var jobrecentlist = arrayListOf<JobRecent>()
    var allpostList = arrayListOf<AllPostClass>()
    val createClient = UserAPI.create()

    var KEY_LOGIN_ID : Int? = null
    var KEY_ROLE : Int? = null
    var KEY_USERNAME : String? = null
    var KEY_FULLNAME : String? = null
    var KEY_EMAIL : String? = null
    var KEY_PHONE : String? = null
    var KEY_BIRTHDAY : String? = null
    var KEY_NICKNAME : String? = null
    var KEY_GENDER : String? = null
    var KEY_COUNTRY_ID : Int? = null
    var KEY_PICTURE : String? = null
    var KEY_EMPCARD : String? = null
    var KEY_DEPT : String? = null
    var KEY_COMPANY_ID : Int? = null
    var KEY_STATUS : Int? = null
    var KEY_EMPLOYER_ID : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployerHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(applicationContext)
        // Read data from the preferences
        KEY_USERNAME = session.pref.getString(SessionManager.KEY_USERNAME, null)
        KEY_USERNAME?.let { getUserData(it) }

        binding.btnPostjob.setOnClickListener {
            var i: Intent = Intent(applicationContext, PostJob1Activity::class.java)
            startActivity(i)
            finish()
        }

        binding.bottomNav.selectedItemId = R.id.home
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    it.isChecked = true
                }
                R.id.application -> {
                    val i: Intent = Intent(applicationContext, EmployerApplicationActivity::class.java)
                    i.putExtra("KEY_EMPLOYER_ID" , KEY_EMPLOYER_ID.toString())
                    startActivity(i)
                    finish()
                    it.isChecked = true
                }
                R.id.profile -> {
                    val i: Intent = Intent(applicationContext, ProfileActivity::class.java)
                    startActivity(i)
                    finish()
                    it.isChecked = true
                }
            }
            true
        }
        //Link to Recyclerview
        binding.recyclerViewAllPost.adapter =
            AllPostAdapter(this.allpostList, applicationContext)
        binding.recyclerViewAllPost.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerViewAllPost.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerViewAllPost.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onResume() {
        super.onResume()
        session = SessionManager(applicationContext)
        // Read data from the preferences
        KEY_USERNAME = session.pref.getString(SessionManager.KEY_USERNAME, null)
        KEY_USERNAME?.let { getUserData(it) }
        callAllPostData()
        callJobRecentData()

    }
    private fun getUserData(msg: String) {
        createClient.fetchUserData(
            username = msg
        ).enqueue(object : Callback<UserDataClass> {
            override fun onResponse(call: Call<UserDataClass>, response: Response<UserDataClass>) {
                if(response.isSuccessful) {
                    KEY_LOGIN_ID = response.body()?.Login_id
                    KEY_ROLE = response.body()?.Login_role
                    KEY_FULLNAME = response.body()?.fullName
                    KEY_EMAIL = response.body()?.email
                    KEY_PHONE = response.body()?.phone
                    KEY_BIRTHDAY = response.body()?.birthday
                    KEY_NICKNAME = response.body()?.nickName
                    KEY_GENDER = if(response.body()?.gender == 0) "Male" else "Female"
                    KEY_COUNTRY_ID = response.body()?.country_id
                    KEY_PICTURE = response.body()?.picture_emp
                    KEY_EMPCARD = response.body()?.empcard
                    KEY_DEPT = response.body()?.dept
                    KEY_COMPANY_ID = response.body()?.company_id
                    KEY_STATUS = response.body()?.status
                    KEY_EMPLOYER_ID = response.body()?.employer_id

                    binding.txtUsername.text = response.body()?.fullName

                    Glide.with(applicationContext)
                        .load(KEY_PICTURE)
                        .into(binding.imgUser)
                } else {
                    Toast.makeText(applicationContext,"Failure on calling user data...", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<UserDataClass>, t: Throwable) {
                Toast.makeText(applicationContext,"Failed on "+t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun callAllPostData(){
        allpostList.clear()
        val serv : AllPostAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AllPostAPI ::class.java)
        serv.getAllPost(
            employer_id = 2
        )
            .enqueue(object : Callback<List<AllPostClass>> {
                override fun onResponse(
                    call: Call<List<AllPostClass>>,
                    response: Response<List<AllPostClass>>,
                ) {
                    response.body()?.forEach {
                        allpostList.add(AllPostClass(it.job_name, it.salaryFrom, it.salaryTo, it.type, it.logo, it.nicename, it.company_name))
                    }
                    binding.recyclerViewAllPost.layoutManager = LinearLayoutManager(applicationContext)
                    binding.recyclerViewAllPost.adapter = AllPostAdapter(allpostList, applicationContext)
                }

                override fun onFailure(call: Call<List<AllPostClass>>, t: Throwable) {
                    Toast.makeText(applicationContext,"Error onFailure" + t.message,Toast.LENGTH_LONG).show()
                }

            })
    }

    private fun callJobRecentData() {
        jobrecentlist.clear()
        val serv: JobRecentAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JobRecentAPI::class.java)
        serv.getJobsRecent()
            .enqueue(object : Callback<List<JobRecent>> {
                override fun onResponse(
                    call: Call<List<JobRecent>>,
                    response: Response<List<JobRecent>>
                ) {
                    if (response.isSuccessful) {
                        val list = response.body()
                        if (list != null && list.isNotEmpty()) {
                            jobrecentlist.addAll(list.sortedByDescending { it.created_at })

                            binding.JobRecentRycyclerView.layoutManager =
                                LinearLayoutManager(applicationContext)
                            binding.JobRecentRycyclerView.adapter =
                                JobRecentAdapter(jobrecentlist, applicationContext)
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "No job recent data found",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Error onResponse: " + response.message(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }


                override fun onFailure(call: Call<List<JobRecent>>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Error onFailure: " + t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

}
