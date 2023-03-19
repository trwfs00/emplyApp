package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.emplyapp.databinding.ActivityProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding
    private lateinit var session: SessionManager
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNav.selectedItemId = R.id.profile

        session = SessionManager(applicationContext)
        // Read data from the preferences
        KEY_USERNAME = session.pref.getString(SessionManager.KEY_USERNAME, null)
        KEY_USERNAME?.let { getUserData(it) }

        binding.btnPersonalInformation.setOnClickListener{
            var i: Intent = Intent(applicationContext, EditProfileActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.btnPrivacyPolicy.setOnClickListener{
            var i: Intent = Intent(applicationContext, EditProfileActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.btnLogout.setOnClickListener(){
            val edit = session.edior
            edit.clear()
            edit.commit()
            var i: Intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
            finish()
        }
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val i: Intent = Intent(applicationContext, if(KEY_ROLE != 1) HomeActivity::class.java else EmployerHomeActivity::class.java)
                    startActivity(i)
                    finish()
                }
                R.id.application -> {
                    val i: Intent = Intent(applicationContext, if(KEY_ROLE != 1) ApplicationActivity::class.java else EmployerApplicationActivity::class.java)
                    startActivity(i)
                    finish()
                }
                R.id.profile -> {
                    it.isChecked = true
                }
            }
            true
        }
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
}