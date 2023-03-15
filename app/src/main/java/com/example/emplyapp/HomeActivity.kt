package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.emplyapp.databinding.ActivityHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var session: SessionManager
    val createClient = UserAPI.create()

    var KEY_LOGIN_ID :String? = null
    var KEY_ROLE :String? = null
    var KEY_USERNAME :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNav.selectedItemId = R.id.home

        session = SessionManager(applicationContext)
        // Read data from the preferences
        KEY_USERNAME = session.pref.getString(SessionManager.KEY_USERNAME, null)
        callDataUserId(KEY_USERNAME!!)
        binding.txtUsername.text = KEY_USERNAME

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

    override fun onResume() {
        super.onResume()
        callDataUserId(KEY_USERNAME.toString())
    }
}