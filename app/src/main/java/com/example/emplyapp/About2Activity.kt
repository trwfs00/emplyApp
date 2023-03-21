package com.example.emplyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.emplyapp.databinding.ActivityAbout1Binding
import com.example.emplyapp.databinding.ActivityAbout2Binding

class About2Activity : AppCompatActivity() {
    private lateinit var bindingAbout2: ActivityAbout2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingAbout2 = ActivityAbout2Binding.inflate(layoutInflater)
        setContentView(bindingAbout2.root)
    }
}