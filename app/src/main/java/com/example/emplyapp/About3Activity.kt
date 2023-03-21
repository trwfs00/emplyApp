package com.example.emplyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.emplyapp.databinding.ActivityAbout1Binding
import com.example.emplyapp.databinding.ActivityAbout3Binding

class About3Activity : AppCompatActivity() {
    private lateinit var bindingAbout3: ActivityAbout3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingAbout3 = ActivityAbout3Binding.inflate(layoutInflater)
        setContentView(bindingAbout3.root)
    }
}