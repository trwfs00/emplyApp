package com.example.emplyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.example.emplyapp.databinding.ActivityAbout1Binding

class About1Activity : AppCompatActivity() {
    private lateinit var bindingAbout1: ActivityAbout1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingAbout1 = ActivityAbout1Binding.inflate(layoutInflater)
        setContentView(bindingAbout1.root)

    }
}