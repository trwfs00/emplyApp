package com.example.emplyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isNotEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emplyapp.databinding.ActivitySearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity(), SearchAdapter.onItemClickListener {
    private lateinit var bindingSearchActivity: ActivitySearchBinding
    private lateinit var searchView: SearchView

    private var SearchList = ArrayList<SearchClass>()
    val searchClient = SearchAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingSearchActivity = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(bindingSearchActivity.root)

        searchView = bindingSearchActivity.searchViewSearch

    }

    override fun onResume() {
        super.onResume()
        setupSearchView()
        callJobData()

    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(newText: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    callJobData()
                    bindingSearchActivity.result.text = SearchList.size.toString() + " Found"
                } else {
                    fetchJobs(newText)
                    bindingSearchActivity.result.text = SearchList.size.toString() + " Found"
                }
                return true
            }

        })
    }

    fun callJobData() {
        SearchList.clear()
        searchClient.getAllSearch()
            .enqueue(object : Callback<List<SearchClass>> {
                override fun onResponse(
                    call: Call<List<SearchClass>>,
                    response: Response<List<SearchClass>>
                ) {
                    response.body()?.forEach {
                        SearchList.add(SearchClass(it.job_name, it.company_name, it.country_name, it.state, it.salaryFrom, it.salaryTo, it.type, it.description, it.minimumQualification, it.benefit))
                    }
                    bindingSearchActivity.recyclerViewApp.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                    bindingSearchActivity.recyclerViewApp.adapter = SearchAdapter(SearchList, this@SearchActivity ,applicationContext)
                    bindingSearchActivity.result.text = SearchList.size.toString() + " Found"
                }

                override fun onFailure(call: Call<List<SearchClass>>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }

    fun fetchJobs(key: String) {
        SearchList.clear()
        if (bindingSearchActivity.searchViewSearch.isNotEmpty()) {
            searchClient.getSearch(key)
                .enqueue(object : Callback<List<SearchClass>>, CountryAdapter.onItemClickListener {
                    override fun onResponse(
                        call: Call<List<SearchClass>>,
                        response: Response<List<SearchClass>>
                    ) {
                        response.body()?.forEach {
                            SearchList.add(SearchClass(it.job_name, it.company_name, it.country_name, it.state, it.salaryFrom, it.salaryTo, it.type, it.description, it.minimumQualification, it.benefit))
                        }
                        bindingSearchActivity.recyclerViewApp.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                        bindingSearchActivity.recyclerViewApp.adapter = SearchAdapter(SearchList, this@SearchActivity ,applicationContext)
                        bindingSearchActivity.result.text = SearchList.size.toString() + " Found"
                    }

                    override fun onFailure(call: Call<List<SearchClass>>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            "Error onFailure." + t.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onClick(position: Int) {

                    }


                })
        }

    }

    override fun onClick(position: Int) {

    }
}