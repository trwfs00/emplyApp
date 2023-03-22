package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.emplyapp.databinding.ActivityJobDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityJobDetail : AppCompatActivity() {
    private lateinit var bindingJobDetail : ActivityJobDetailBinding

    private var SearchList = ArrayList<SearchClass>()
    val searchClient = SearchAPI.create()

    var KEY_NAME_JOBS :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingJobDetail = ActivityJobDetailBinding.inflate(layoutInflater)
        setContentView(bindingJobDetail.root)

    }

    override fun onResume() {
        super.onResume()
        callJobData()

        bindingJobDetail.btnApply.setOnClickListener {
            var intent: Intent = Intent(applicationContext, ApplyJobActivity::class.java)
            intent.putExtra("jobName",KEY_NAME_JOBS)
            startActivity(intent)
        }
    }

    fun callJobData() {
        var data = intent.extras
        var newS:SearchClass? = data?.getParcelable("jobData")
        SearchList.clear()
        searchClient.getSearch(newS?.job_name.toString())
            .enqueue(object : Callback<List<SearchClass>> {
                override fun onResponse(
                    call: Call<List<SearchClass>>,
                    response: Response<List<SearchClass>>
                ) {
                    response.body()?.forEach {
                        SearchList.add(SearchClass(it.job_id, it.job_name, it.company_name, it.country_name, it.nicename, it.state, it.salaryFrom, it.salaryTo, it.type, it.description, it.minimumQualification, it.benefit, it.category_name, it.logo))
                    }
                    Glide.with(this@ActivityJobDetail).load(SearchList[0].logo).into(bindingJobDetail.jobImage)
                    bindingJobDetail.jobName.text = SearchList[0].job_name
                    bindingJobDetail.jobInc.text = SearchList[0].company_name
                    bindingJobDetail.jobLocation.text = SearchList[0].nicename + " " + SearchList[0].state
                    bindingJobDetail.jobSalary.text = SearchList[0].salaryFrom.toString() + " - " + SearchList[0].salaryTo.toString()
                    bindingJobDetail.jobType.text = SearchList[0].type
                    bindingJobDetail.JobDescriptionDetail.text = SearchList[0].description
                    bindingJobDetail.MinimumDetail.text = SearchList[0].minimumQualification
                    bindingJobDetail.BenefitsDetail.text = SearchList[0].benefit
                    bindingJobDetail.CategoryDetail.text = SearchList[0].category_name
                    KEY_NAME_JOBS = SearchList[0].job_name.toString()


                    Toast.makeText(applicationContext, SearchList[0].category_name.toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<List<SearchClass>>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }
}