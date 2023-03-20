package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.emplyapp.databinding.ActivityPostJob2Binding

class PostJob2Activity : AppCompatActivity() {
    private lateinit var bindingPJ2 : ActivityPostJob2Binding

    var KEY_NAME :String? = null
    var KEY_DESCRIPTION :String? = null
    var KEY_QUALIFICATIONS :String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingPJ2 = ActivityPostJob2Binding.inflate(layoutInflater)
        setContentView(bindingPJ2.root)

        var intent: Intent = getIntent()
        KEY_NAME = intent.getStringExtra("NAME")
        KEY_DESCRIPTION = intent.getStringExtra("DESCRIPTION")
        bindingPJ2.txtCheckValue.text = KEY_NAME+" "+KEY_DESCRIPTION

        bindingPJ2.btnContinue.setOnClickListener {
            KEY_QUALIFICATIONS = bindingPJ2.edtQualifications.text.toString()
            if (KEY_QUALIFICATIONS != null) {
                val i = Intent(applicationContext,PostJob3Activity::class.java)
                i.putExtra("NAME" , KEY_NAME)
                i.putExtra("DESCRIPTION" , KEY_DESCRIPTION)
                i.putExtra("QUALIFICATIONS" , KEY_QUALIFICATIONS)
                startActivity(i)
            } else {
                Toast.makeText(applicationContext,"Please Enter Minimum Qualifications", Toast.LENGTH_LONG).show()
            }
        }
    }
}