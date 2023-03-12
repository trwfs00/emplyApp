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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(applicationContext)
        if (session.isLoggedIn()) {
            var i: Intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(i)
            finish()
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
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Enter username and password.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
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

            }
        }
    }
}