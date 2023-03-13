package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.emplyapp.databinding.ActivityProfileBinding
import com.example.emplyapp.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID

class RegisterActivity : AppCompatActivity() {
    private lateinit var bindingRegister : ActivityRegisterBinding
    lateinit var session: SessionManager
    val createClient = UserAPI.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingRegister = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bindingRegister.root)

        session = SessionManager(applicationContext)
        bindingRegister.txtSignIn.setOnClickListener {
            var i: Intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
    fun generateUserId(): String {
        val uuid = UUID.randomUUID().toString().replace("-", "")
        return uuid.substring(0, 11)
    }

    fun addUserJob(v: View) {

        var edtUsername = bindingRegister.edtUsername.text.toString()
        var edtPassword = bindingRegister.edtPassword.text.toString()
        var edtConPassword = bindingRegister.edtConPassword.text.toString()

        if (edtPassword.isEmpty() || edtUsername.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Please complete your information",
                Toast.LENGTH_SHORT
            ).show()

        } else if (edtPassword == edtConPassword) {
            createClient.insertUser(
                username = edtUsername,
                password = edtPassword,
                role = 0
            ).enqueue(object : Callback<RoleClass> {
                override fun onResponse(call: Call<RoleClass>, response: Response<RoleClass>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Successfully Register",
                            Toast.LENGTH_LONG
                        ).show()
                        val id_back = response.body()?.Login_id.toString()
                        val username_back = response.body()?.username.toString()
                        session.createLoginSession(username_back, id_back, edtUsername)
                        var i: Intent = Intent(applicationContext, CountryActivity::class.java)
                        startActivity(i)
                        finish()
                    } else if (response.code() == 500) {
                        Toast.makeText(applicationContext, "Duplicate Username", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(applicationContext, "Failed Register ", Toast.LENGTH_LONG)
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
        } else {
            Toast.makeText(applicationContext, "Password do not match", Toast.LENGTH_SHORT).show()
        }
    }

    fun addUserEmp(v: View) {
        var edtUsername = bindingRegister.edtUsername.text.toString()
        var edtPassword = bindingRegister.edtPassword.text.toString()
        var edtConPassword = bindingRegister.edtConPassword.text.toString()

        if (edtPassword.isEmpty() || edtUsername.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Please complete your information",
                Toast.LENGTH_SHORT
            ).show()

        } else if (edtPassword == edtConPassword) {
            createClient.insertUser(
                username = edtUsername,
                password = edtPassword,
                role = 1
            ).enqueue(object : Callback<RoleClass> {
                override fun onResponse(call: Call<RoleClass>, response: Response<RoleClass>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Successfully Register",
                            Toast.LENGTH_LONG
                        ).show()
                        val id_back = response.body()?.Login_id.toString()
                        val username_back = response.body()?.username.toString()
                        session.createLoginSession(username_back, id_back, edtUsername)

                        var i: Intent = Intent(applicationContext, CountryActivity::class.java)
                        startActivity(i)
                        finish()
                    } else if (response.code() == 500) {
                        Toast.makeText(applicationContext, "Duplicate Username", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(applicationContext, "Failed Register ", Toast.LENGTH_LONG)
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
        } else {
            Toast.makeText(applicationContext, "Password do not match", Toast.LENGTH_SHORT).show()
        }
    }
}