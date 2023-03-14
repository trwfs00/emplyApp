package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.emplyapp.databinding.ActivityCompanyBinding
import com.example.emplyapp.databinding.ActivityCreateProfileBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class CompanyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompanyBinding
    private lateinit var session: SessionManager

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

    var KEY_COMPANY_NAME :String? = null
    var KEY_COMPANY_COUNTRY :String? = null
    var KEY_COMPANY_STATE :String? = null
    var KEY_COMPANY_ADDRESS :String? = null
    var KEY_COMPANY_IMAGE_PATH :String? = null

    val createCompany = UserAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyBinding.inflate(layoutInflater)
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

        binding.img.setOnClickListener {
            showInputDialog()
        }

        binding.btnContinue.setOnClickListener {
            KEY_COMPANY_NAME = binding.edtCompanyName.text.toString()
            KEY_COMPANY_COUNTRY = binding.edtCountry.text.toString()
            KEY_COMPANY_STATE = binding.edtState.text.toString()
            KEY_COMPANY_ADDRESS = binding.edtAddress.text.toString()

            if(KEY_COMPANY_NAME != null && KEY_COMPANY_COUNTRY != null && KEY_COMPANY_STATE != null && KEY_COMPANY_ADDRESS != null && KEY_COMPANY_IMAGE_PATH != null) {
                createCompany.insertCompany(
                    name = KEY_COMPANY_NAME.toString(),
                    country = KEY_COMPANY_COUNTRY.toString(),
                    state = KEY_COMPANY_STATE.toString(),
                    address = KEY_COMPANY_ADDRESS.toString(),
                    logo = KEY_COMPANY_IMAGE_PATH.toString()
                ).enqueue(object : Callback<CompanyClass> {
                    override fun onResponse(
                        call: Call<CompanyClass>,
                        response: Response<CompanyClass>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Successfully create profile",
                                Toast.LENGTH_LONG
                            ).show()
                            val i: Intent = Intent(applicationContext, Company2Activity::class.java)
                            val KEY_COMPANY_ID: String = response.body()?.company_id.toString()
                            i.putExtra("login_id", KEY_LOGIN_ID)
                            i.putExtra("username", KEY_USERNAME)
                            i.putExtra("fullname", KEY_FULLNAME)
                            i.putExtra("nickname", KEY_NICKNAME)
                            i.putExtra("birthday", KEY_BIRTHDAY)
                            i.putExtra("phone", KEY_PHONE)
                            i.putExtra("gender", KEY_GENDER)
                            i.putExtra("email", KEY_EMAIL)
                            i.putExtra("country_id", KEY_COUNTRY_ID)
                            i.putExtra("image", KEY_IMAGE_PATH)
                            i.putExtra("company_id", KEY_COMPANY_ID)
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

                    override fun onFailure(call: Call<CompanyClass>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG)
                            .show()
                    }
                })
            } else if (KEY_COMPANY_NAME === null) {
                Toast.makeText(applicationContext,"Company Name is empty, Please provide us",Toast.LENGTH_SHORT).show()
            } else if (KEY_COMPANY_COUNTRY === null) {
                Toast.makeText(applicationContext,"Company Country is empty, Please provide us",Toast.LENGTH_SHORT).show()
            } else if (KEY_COMPANY_STATE === null) {
                Toast.makeText(applicationContext,"Company State is empty, Please provide us",Toast.LENGTH_SHORT).show()
            } else if (KEY_COMPANY_ADDRESS === null) {
                Toast.makeText(applicationContext,"Company Address is empty, Please provide us",Toast.LENGTH_SHORT).show()
            } else if (KEY_COMPANY_IMAGE_PATH === null) {
                Toast.makeText(applicationContext,"Company Image is empty, Please provide us",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext,"Error Please Contact Admin",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showInputDialog() {
        val dialogBuilder = AlertDialog.Builder(this)

        val editText = EditText(this)
        dialogBuilder.setTitle("PHOTO URL")
            .setView(editText)
            .setPositiveButton("OK") { dialogInterface, i ->
                val inputText = editText.text.toString()
                KEY_COMPANY_IMAGE_PATH = inputText
                Toast.makeText(applicationContext, "You entered: $inputText", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialogInterface, i ->
                dialogInterface.cancel()
            }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
}