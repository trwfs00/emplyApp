package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.emplyapp.databinding.ActivityPostJob3Binding

class PostJob3Activity : AppCompatActivity() {
    private lateinit var bindingPJ3 : ActivityPostJob3Binding

    var KEY_NAME :String? = null
    var KEY_DESCRIPTION :String? = null
    var KEY_QUALIFICATIONS :String? = null
    var KEY_PAB :String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingPJ3 = ActivityPostJob3Binding.inflate(layoutInflater)
        setContentView(bindingPJ3.root)

        var intent: Intent = getIntent()
        KEY_NAME = intent.getStringExtra("NAME")
        KEY_DESCRIPTION = intent.getStringExtra("DESCRIPTION")
        KEY_QUALIFICATIONS = intent.getStringExtra("QUALIFICATIONS")
        bindingPJ3.txtCheckValue.text = KEY_NAME+" "+KEY_DESCRIPTION+" "+KEY_QUALIFICATIONS

        bindingPJ3.btnContinue.setOnClickListener {
            KEY_PAB = bindingPJ3.edtPerksandBenefits.text.toString()
            if (KEY_PAB != null) {
                val i = Intent(applicationContext,PostJob4Activity::class.java)
                i.putExtra("NAME" , KEY_NAME)
                i.putExtra("DESCRIPTION" , KEY_DESCRIPTION)
                i.putExtra("QUALIFICATIONS" , KEY_QUALIFICATIONS)
                i.putExtra("PAB" , KEY_PAB)
                startActivity(i)
            } else {
                Toast.makeText(applicationContext,"Please Enter Perks and Benefits", Toast.LENGTH_LONG).show()
            }
        }
    }
}