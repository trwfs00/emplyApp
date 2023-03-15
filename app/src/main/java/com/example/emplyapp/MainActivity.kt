package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.emplyapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var session: SessionManager
    val createClient = UserAPI.create()
    var KEY_ROLE :String? = null
    var KEY_LOGIN_ID :String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(applicationContext)
        if (session.isLoggedIn()) {
            callDataUserId(session.pref.getString(SessionManager.KEY_USERNAME, null).toString())
            if(KEY_ROLE?.toInt() == 0) {
                var i: Intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(i)
                finish()
            } else {
                var i: Intent = Intent(applicationContext, EmployerHomeActivity::class.java)
                startActivity(i)
                finish()
            }
        }
        if (!session.pref.getString(SessionManager.KEY_USERNAME, null).isNullOrEmpty()) {
            val username: String? = session.pref.getString(SessionManager.KEY_USERNAME, null)
            binding.edtUsername.setText(username)
        }
        binding.txtSignUp.setOnClickListener {
            var i = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.btnLogin.setOnClickListener() {
            var username = binding.edtUsername.text.toString()
            var password = binding.edtPassword.text.toString()
            callDataUserId(username)
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Enter username and password.",
                    Toast.LENGTH_LONG
                ).show()
            } else if (KEY_ROLE?.toInt() == 0) {
                createClient.userLogin(username, password)
                    .enqueue(object : Callback<LoginUserClass> {
                        override fun onResponse(
                            call: Call<LoginUserClass>,
                            response: Response<LoginUserClass>
                        ) {
                            val success = response.body()?.success.toString().toInt()
                            if (success == 0) {
                                Toast.makeText(
                                    applicationContext,
                                    "The username or password is incorrect",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                val id_back = response.body()?.userid.toString()
                                val username_back = response.body()?.username.toString()

                                session.createLoginSession(username_back, id_back, username)
                                var i: Intent =
                                    Intent(applicationContext, HomeActivity::class.java)
                                startActivity(i)
                                finish()
                            }
                        }

                        override fun onFailure(call: Call<LoginUserClass>, t: Throwable) {
                            print(t.message)
                            Toast.makeText(
                                applicationContext,
                                "Error onFailure " + t.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })

            } else if (KEY_ROLE?.toInt() == 1) {
                createClient.userLogin(username, password)
                    .enqueue(object : Callback<LoginUserClass> {
                        override fun onResponse(
                            call: Call<LoginUserClass>,
                            response: Response<LoginUserClass>
                        ) {
                            val success = response.body()?.success.toString().toInt()
                            if (success == 0) {
                                Toast.makeText(
                                    applicationContext,
                                    "The username or password is incorrect",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                val id_back = response.body()?.userid.toString()
                                val username_back = response.body()?.username.toString()

                                session.createLoginSession(username_back, id_back, username)
                                var i: Intent =
                                    Intent(applicationContext, EmployerHomeActivity::class.java)
                                startActivity(i)
                                finish()
                            }
                        }

                        override fun onFailure(call: Call<LoginUserClass>, t: Throwable) {
                            print(t.message)
                            Toast.makeText(
                                applicationContext,
                                "Error onFailure " + t.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
            }
        }
    }
    private fun callDataUserId(username: String) {
        createClient.getLoginId(
            username = username
        ).enqueue(object : Callback<RoleClass> {
            override fun onResponse(call: Call<RoleClass>, response: Response<RoleClass>) {
                if (response.isSuccessful) {
                    KEY_LOGIN_ID = response.body()!!.Login_id.toString()
                    KEY_ROLE = response.body()!!.Login_role.toString()
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