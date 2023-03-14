package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.emplyapp.databinding.ActivityCompany2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Company2Activity : AppCompatActivity() {
    private lateinit var binding : ActivityCompany2Binding

    var KEY_LOGIN_ID :String? = null
    var KEY_USERNAME :String? = null
    var KEY_FULLNAME :String? = null
    var KEY_NICKNAME :String? = null
    var KEY_BIRTHDAY :String? = null
    var KEY_PHONE :String? = null
    var KEY_GENDER :String? = null
    var KEY_EMAIL :String? = null
    var KEY_COUNTRY_ID :String? = null
    var KEY_IMAGE_PATH :String? = null

    var KEY_COMPANY_ID: String? = null
    var KEY_EMPLOYER_ID: String? = null
    var KEY_EMPLOYER_DEPT: String? = null

    val createEmployer = UserAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompany2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent: Intent = getIntent()
        KEY_LOGIN_ID = intent.getStringExtra("login_id")
        KEY_USERNAME = intent.getStringExtra("username")
        KEY_FULLNAME = intent.getStringExtra("fullname")
        KEY_NICKNAME = intent.getStringExtra("nickname")
        KEY_BIRTHDAY = intent.getStringExtra("birthday")
        KEY_PHONE = intent.getStringExtra("phone")
        KEY_GENDER = intent.getStringExtra("gender")
        KEY_EMAIL = intent.getStringExtra("email")
        KEY_COUNTRY_ID = intent.getStringExtra("country_id")
        KEY_IMAGE_PATH = intent.getStringExtra("image")
        KEY_COMPANY_ID = intent.getStringExtra("company_id")

        binding.btnContinue.setOnClickListener {
            KEY_EMPLOYER_ID = binding.edtEmpId.text.toString()
            KEY_EMPLOYER_DEPT = binding.edtDept.text.toString()

            if(KEY_EMPLOYER_ID != null && KEY_EMPLOYER_DEPT != null) {
                createEmployer.insertEmployer(
                    fullName = KEY_FULLNAME.toString(),
                    nickName = KEY_NICKNAME.toString(),
                    birthday = KEY_BIRTHDAY.toString(),
                    phone = KEY_PHONE.toString(),
                    gender = KEY_GENDER!!.toInt(),
                    email = KEY_EMAIL.toString(),
                    status = 0,
                    dept = KEY_EMPLOYER_DEPT.toString(),
                    Login_id = KEY_LOGIN_ID!!.toInt(),
                    company_id = KEY_COMPANY_ID!!.toInt(),
                    country_id = KEY_COUNTRY_ID!!.toInt()
                ).enqueue(object : Callback<EmployerClass> {
                    override fun onResponse(
                        call: Call<EmployerClass>,
                        response: Response<EmployerClass>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Successfully create profile",
                                Toast.LENGTH_LONG
                            ).show()
                            val i: Intent = Intent(applicationContext, HomeActivity::class.java)
                            startActivity(i)
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Failed to find login_id",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<EmployerClass>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            } else if (KEY_EMPLOYER_ID === null) {
                Toast.makeText(applicationContext,"Please provide your Employee ID",Toast.LENGTH_SHORT).show()
            } else if (KEY_EMPLOYER_DEPT === null) {
                Toast.makeText(applicationContext,"Please provide your Dept",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext,"Error Please Contact Admin", Toast.LENGTH_SHORT).show()
            }
        }
    }
}