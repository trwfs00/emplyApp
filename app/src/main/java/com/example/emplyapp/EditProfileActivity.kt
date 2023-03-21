package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.emplyapp.databinding.ActivityEditProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
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
    var KEY_NEW_PICTURE : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(applicationContext)
        // Read data from the preferences
        KEY_USERNAME = session.pref.getString(SessionManager.KEY_USERNAME, null)
        getUserData(KEY_USERNAME.toString())

        binding.imgProfile.setOnClickListener {
            showInputDialog()
        }

        binding.btnSave.setOnClickListener {
            var KEY_EDIT_FULLNAME = binding.edtFullname.text.toString()
            var KEY_EDIT_NICKNAME = binding.edtNickname.text.toString()
            var KEY_EDIT_BIRTHDAY = binding.edtDateBirthday.text.toString()
            getUserData(KEY_USERNAME.toString())
            if (KEY_ROLE == 0){
                createClient.editJobseeker(
                    fullName = KEY_EDIT_FULLNAME,
                    nickName = KEY_EDIT_NICKNAME,
                    birthday = KEY_EDIT_BIRTHDAY,
                    picture_jobseek = if(KEY_NEW_PICTURE.isNullOrEmpty()) KEY_PICTURE.toString() else KEY_NEW_PICTURE.toString(),
                    Login_id = KEY_LOGIN_ID!!
                ).enqueue(object : Callback<ProfileClass>{
                    override fun onResponse(
                        call: Call<ProfileClass>,
                        response: Response<ProfileClass>
                    ) {
                        if (response.isSuccessful){
                            Toast.makeText(
                                applicationContext,
                                "Successfully updated",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            val i: Intent = Intent(applicationContext, ProfileActivity::class.java)
                            startActivity(i)
                        }else{
                            Toast.makeText(
                                applicationContext,
                                "Failed update.",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                    override fun onFailure(call: Call<ProfileClass>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG)
                            .show()
                    }
                })
            }else if (KEY_ROLE == 1) {
                createClient.editEmployer(
                    fullName = KEY_EDIT_FULLNAME,
                    nickName = KEY_EDIT_NICKNAME,
                    birthday = KEY_EDIT_BIRTHDAY,
                    picture_emp = if(KEY_NEW_PICTURE.isNullOrEmpty()) KEY_PICTURE.toString() else KEY_NEW_PICTURE.toString(),
                    Login_id = KEY_LOGIN_ID!!
                ).enqueue(object : Callback<ProfileClass>{
                    override fun onResponse(call: Call<ProfileClass>, response: Response<ProfileClass>) {
                        if (response.isSuccessful){
                            Toast.makeText(
                                applicationContext,
                                "Successfully updated",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            val i: Intent = Intent(applicationContext, ProfileActivity::class.java)
                            startActivity(i)
                        }else{
                            Toast.makeText(
                                applicationContext,
                                "Failed to update data.",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                    override fun onFailure(call: Call<ProfileClass>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG)
                            .show()
                    }
                })
            }
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
                    KEY_PICTURE = if(response.body()?.Login_role == 0) response.body()?.picture_jobseek else response.body()?.picture_emp
                    binding.edtFullname.hint = response.body()?.fullName

                    Glide.with(applicationContext)
                        .load(KEY_PICTURE)
                        .into(binding.imgProfile)
                } else {
                    Toast.makeText(applicationContext,"Failure on calling user data...", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<UserDataClass>, t: Throwable) {
                Toast.makeText(applicationContext,"Failed on "+t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showInputDialog() {
        val dialogBuilder = AlertDialog.Builder(this)

        val editText = EditText(this)
        editText.setText(if(KEY_PICTURE != null) KEY_PICTURE else "")
        dialogBuilder.setTitle("PHOTO URL")
            .setView(editText)
            .setPositiveButton("OK") { dialogInterface, i ->
                val inputText = editText.text.toString()
                if (isValidImageUrl(inputText)) {
                    KEY_NEW_PICTURE = inputText
                    Glide.with(applicationContext)
                        .load(KEY_NEW_PICTURE)
                        .into(binding.imgProfile)
                    Toast.makeText(applicationContext, "You entered: $inputText", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Invalid image URL", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialogInterface, i ->
                dialogInterface.cancel()
            }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
    private fun isValidImageUrl(url: String): Boolean {
        val pattern = ".*\\.(jpg|jpeg|png|gif|bmp)".toRegex()
        return pattern.matches(url)
    }
}