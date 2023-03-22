package com.example.emplyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.emplyapp.databinding.ActivityApplyJobBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplyJobActivity : AppCompatActivity() {
    private lateinit var binding : ActivityApplyJobBinding
    lateinit var session: SessionManager

    private var SearchList = ArrayList<SearchClass>()
    val searchClient = SearchAPI.create()

    var PDF_PATH: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUploadResume.setOnClickListener {
            showInputDialog()
        }

    }

    override fun onResume() {
        super.onResume()
        callJobData()

    }

    fun callJobData() {
        var jobName = intent.getStringExtra("jobName")
        SearchList.clear()
        searchClient.getSearch(jobName.toString())
            .enqueue(object : Callback<List<SearchClass>> {
                override fun onResponse(
                    call: Call<List<SearchClass>>,
                    response: Response<List<SearchClass>>
                ) {
                    response.body()?.forEach {
                        SearchList.add(SearchClass(it.job_id, it.job_name, it.company_name, it.country_name, it.nicename, it.state, it.salaryFrom, it.salaryTo, it.type, it.description, it.minimumQualification, it.benefit, it.category_name, it.logo))
                    }
                    Toast.makeText(applicationContext, SearchList[0].job_id.toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<List<SearchClass>>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }

    private fun showInputDialog() {
        val dialogBuilder = AlertDialog.Builder(this)

        val editText = EditText(this)
        dialogBuilder.setTitle("PHOTO URL")
            .setView(editText)
            .setPositiveButton("OK") { dialogInterface, i ->
                val inputText = editText.text.toString()
                PDF_PATH = inputText
                Toast.makeText(applicationContext, "You entered: $inputText", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialogInterface, i ->
                dialogInterface.cancel()
            }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
}