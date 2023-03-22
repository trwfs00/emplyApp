package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.emplyapp.databinding.ActivityHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var session: SessionManager
    var categoryList = arrayListOf<CategoryClass>()
    var jobrecentlist = arrayListOf<JobRecent>()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNav.selectedItemId = R.id.home

        session = SessionManager(applicationContext)
        // Read data from the preferences
        KEY_USERNAME = session.pref.getString(SessionManager.KEY_USERNAME, null)
        KEY_USERNAME?.let { getUserData(it) }

        binding.btnTipsForYou.setOnClickListener {
            val i: Intent = Intent(applicationContext, DetailActivity::class.java)
            startActivity(i)
        }

        binding.txtUsername.text = KEY_USERNAME
        binding.txtUsername.setOnClickListener {
            getUserData(KEY_USERNAME!!)
            binding.txtUsername.text = KEY_FULLNAME
        }
        binding.btnSearch.setOnClickListener {
            val i : Intent = Intent(applicationContext, SearchActivity::class.java)
            startActivity(i)
        }

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

        binding.searchBar.setOnClickListener {
            var intent = Intent(applicationContext, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        callCategoryData()
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
                    KEY_PICTURE = response.body()?.picture_jobseek
                    binding.txtUsername.text = response.body()?.fullName

                    Glide.with(applicationContext)
                        .load(KEY_PICTURE)
                        .into(binding.imgUser)
                } else {
                    Toast.makeText(applicationContext,"Failure on calling user data...",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<UserDataClass>, t: Throwable) {
                Toast.makeText(applicationContext,"Failed on "+t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun callCategoryData(){
        categoryList.clear()
        val serv : CategoryAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoryAPI ::class.java)
        serv.getCategory()
            .enqueue(object : Callback<List<CategoryClass>> {
                override fun onResponse(
                    call: Call<List<CategoryClass>>,
                    response: Response<List<CategoryClass>>,
                ) {
                    response.body()?.forEach {
                        categoryList.add(CategoryClass(it.category_name))
                    }
                    binding.recyclerCategory.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                    binding.recyclerCategory.adapter = CategoryNameAdapter(categoryList, applicationContext)
                }

                override fun onFailure(call: Call<List<CategoryClass>>, t: Throwable) {
                    Toast.makeText(applicationContext,"Error onFailure" + t.message,Toast.LENGTH_LONG).show()
                }

            })
    }

    private fun callJobRecentData(){
        jobrecentlist.clear()
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
                        jobrecentlist.add(JobRecent(it.job_name,it.company_name,it.nicename,it.type,it.logo,it.created_at))
                    }
                    binding.JobRecentRycyclerView.layoutManager = LinearLayoutManager(applicationContext)
                    binding.JobRecentRycyclerView.adapter = JobRecentAdapter(jobrecentlist, applicationContext)
                }

                override fun onFailure(call: Call<List<JobRecent>>, t: Throwable) {
                    Toast.makeText(applicationContext,"Error onFailure" + t.message,Toast.LENGTH_LONG).show()
                }

            })
    }
}