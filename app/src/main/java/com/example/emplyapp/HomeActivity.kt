package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.emplyapp.databinding.ActivityHomeBinding
import com.example.emplyapp.databinding.CategoryItemLayoutBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var session: SessionManager
    var categoryList = arrayListOf<CategoryClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNav.selectedItemId = R.id.home

        session = SessionManager(applicationContext)
        // Read data from the preferences
        val username: String? = session.pref.getString(SessionManager.KEY_USERNAME, null)
        binding.txtUsername.text = username

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

    override fun onResume() {
        super.onResume()
        callCategoryData()
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
                    binding.recyclerCategory.adapter = HomeAdapter(categoryList,applicationContext)
                }

                override fun onFailure(call: Call<List<CategoryClass>>, t: Throwable) {
                    Toast.makeText(applicationContext,"Error onFailure" + t.message,Toast.LENGTH_LONG).show()
                }

            })
    }
}