package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.emplyapp.databinding.ActivityEmployerApplicationBinding

class EmployerApplicationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEmployerApplicationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployerApplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.selectedItemId = R.id.application
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    var i: Intent = Intent(applicationContext, EmployerHomeActivity::class.java)
                    startActivity(i)
                    finish()
                    it.isChecked = true
                }
                R.id.application -> {
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
}