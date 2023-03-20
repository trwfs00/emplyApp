package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.emplyapp.databinding.ActivityPostJob1Binding
import java.security.Key

class PostJob1Activity : AppCompatActivity() {
    private lateinit var bindingPJ1 : ActivityPostJob1Binding

    var KEY_NAME :String? = null
    var KEY_DESCRIPTION :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingPJ1 = ActivityPostJob1Binding.inflate(layoutInflater)
        setContentView(bindingPJ1.root)

        bindingPJ1.btnContinue.setOnClickListener {
            KEY_NAME = bindingPJ1.edtName.text.toString()
            KEY_DESCRIPTION = bindingPJ1.edtDescription.text.toString()
            if (KEY_NAME != null && KEY_DESCRIPTION != null) {
                val i = Intent(applicationContext,PostJob2Activity::class.java)
                i.putExtra("NAME" , KEY_NAME)
                i.putExtra("DESCRIPTION" , KEY_DESCRIPTION)
                startActivity(i)
            } else {
                Toast.makeText(applicationContext,"Please Enter Description", Toast.LENGTH_LONG).show()
            }
        }
    }
}