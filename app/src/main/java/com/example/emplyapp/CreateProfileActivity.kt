package com.example.emplyapp

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.emplyapp.databinding.ActivityCreateProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCreateProfileBinding

    private lateinit var session: SessionManager
    var KEY_COUNTRY_ID: String? = null
    var KEY_COUNTRY_NAME: String? = null
    var KEY_USERNAME: String? = null
    var KEY_ROLE: String? = null
    var KEY_GENDER: Int? = null
    var KEY_IMAGE_PATH: String? = null
    var KEY_LOGIN_ID: String? = null

    val createClient = UserAPI.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(applicationContext)
        KEY_USERNAME = session.pref.getString(SessionManager.KEY_USERNAME, null)
        callDataUserId(KEY_USERNAME.toString())
        var intent: Intent = getIntent()
        KEY_COUNTRY_ID = intent.getStringExtra("COUNTRY_ID")
        KEY_COUNTRY_NAME = intent.getStringExtra("COUNTRY_NAME")
        KEY_ROLE = intent.getStringExtra("ROLE_ID")

        val options = arrayOf("Male", "Female")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, options)
        binding.spinnerGender.adapter = adapter

        binding.spinnerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position) as String
                KEY_GENDER = if (selectedItem == "Male") 0 else 1
                Toast.makeText(applicationContext, "You selected: $selectedItem", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(applicationContext, "Please select your gender", Toast.LENGTH_SHORT).show()
            }
        }

        binding.edtProfile.setOnClickListener {
            showInputDialog()
        }

        binding.btnContinue.setOnClickListener {
            callDataUserId(KEY_USERNAME!!)
            var KEY_FULLNAME = binding.edtFullname.text.toString()
            var KEY_NICKNAME = binding.edtNickname.text.toString()
            var KEY_BIRTHDAY = binding.edtDateBirthday.text.toString()
            var KEY_EMAIL = binding.edtEmail.text.toString()
            var KEY_PHONE = binding.edtPhone.text.toString()
            var CHECK_ROLE: Int = KEY_ROLE!!.toInt()
            if (KEY_FULLNAME != null && KEY_NICKNAME != null && KEY_BIRTHDAY != null && KEY_EMAIL != null && KEY_PHONE != null && KEY_IMAGE_PATH != null) {
                if (CHECK_ROLE === 0) {
                    createClient.insertProfile(
                        fullName = KEY_FULLNAME,
                        nickName = KEY_NICKNAME,
                        birthday = KEY_BIRTHDAY,
                        phone = KEY_PHONE,
                        gender = KEY_GENDER!!,
                        email = KEY_EMAIL,
                        Login_id = KEY_LOGIN_ID!!.toInt(),
                        country_id = KEY_COUNTRY_ID!!.toInt(),
                        picture = KEY_IMAGE_PATH!!
                    ).enqueue(object : Callback<Jobseeker> {
                        override fun onResponse(
                            call: Call<Jobseeker>,
                            response: Response<Jobseeker>
                        ) {
                            if (response.isSuccessful) {
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

                        override fun onFailure(call: Call<Jobseeker>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG)
                                .show()
                            binding.txtCheckValue.text = t.message
                        }
                    })
                } else {
                    val i: Intent = Intent(applicationContext, CompanyActivity::class.java)
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
                    startActivity(i)
                }
            } else if (KEY_IMAGE_PATH === null) {
                Toast.makeText(applicationContext,"Please provide your Photo url",Toast.LENGTH_SHORT).show()
            } else if (KEY_FULLNAME === null) {
                Toast.makeText(applicationContext,"Please provide your Fullname",Toast.LENGTH_SHORT).show()
            } else if (KEY_NICKNAME === null) {
                Toast.makeText(applicationContext,"Please provide your Nickname",Toast.LENGTH_SHORT).show()
            } else if (KEY_BIRTHDAY === null) {
                Toast.makeText(applicationContext,"Please provide your Birthday",Toast.LENGTH_SHORT).show()
            } else if (KEY_EMAIL === null) {
                Toast.makeText(applicationContext,"Please provide your Email",Toast.LENGTH_SHORT).show()
            } else if (KEY_PHONE === null) {
                Toast.makeText(
                    applicationContext,
                    "Please provide your Phone number",
                    Toast.LENGTH_SHORT
                ).show()
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
                KEY_IMAGE_PATH = inputText
                Toast.makeText(applicationContext, "You entered: $inputText", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialogInterface, i ->
                dialogInterface.cancel()
            }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun callDataUserId(username: String) {
        createClient.getLoginId(
            username = username
        ).enqueue(object : Callback<RoleClass> {
            override fun onResponse(call: Call<RoleClass>, response: Response<RoleClass>) {
                if (response.isSuccessful) {
                    KEY_LOGIN_ID = response.body()?.Login_id.toString()
                } else {
                    Toast.makeText(applicationContext, "Failed to find login_id", Toast.LENGTH_LONG)
                        .show()
                }
            }
            override fun onFailure(call: Call<RoleClass>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Error onFailure: " + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }

        })
    }
}