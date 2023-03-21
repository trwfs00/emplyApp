package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emplyapp.databinding.ActivityCountryBinding
import com.example.emplyapp.CountryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class CountryActivity : AppCompatActivity(), CountryAdapter.onItemClickListener {
    private lateinit var bindingCountryActivity: ActivityCountryBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var session: SessionManager

    var KEY_COUNTRY_ID: String? = null
    var KEY_COUNTRY_NAME: String? = null
    var KEY_USERNAME: String? = null
    var KEY_ROLE: String? = null

    private var CountryList = ArrayList<CountryClass>()
    val createClient = CountryAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingCountryActivity = ActivityCountryBinding.inflate(layoutInflater)
        setContentView(bindingCountryActivity.root)

        session = SessionManager(applicationContext)
        // Read data from the preferences
        KEY_USERNAME = session.pref.getString(SessionManager.KEY_USERNAME, null)
        var intent: Intent = getIntent()
        KEY_ROLE = intent.getStringExtra("ROLE_ID")

        recyclerView = bindingCountryActivity.recyclerViewCountry
        searchView = bindingCountryActivity.searchViewCountry
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        bindingCountryActivity.btnContinue.setOnClickListener {
            if(KEY_COUNTRY_ID != null && KEY_COUNTRY_NAME != null && KEY_USERNAME != null) {
                val i = Intent(applicationContext,CreateProfileActivity::class.java)
                i.putExtra("COUNTRY_ID" , KEY_COUNTRY_ID)
                i.putExtra("COUNTRY_NAME" , KEY_COUNTRY_NAME)
                i.putExtra("ROLE_ID" , KEY_ROLE)
                startActivity(i)
            } else {
                Toast.makeText(applicationContext,"Please select your country",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        callCountryData()
        setupSearchView()
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(newText: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                fetchUsers(newText)
                return true
            }

        })
    }

    fun callCountryData() {
        CountryList.clear()
        createClient.getAllCountry()
            .enqueue(object : Callback<List<CountryClass>> {
                override fun onResponse(
                    call: Call<List<CountryClass>>,
                    response: Response<List<CountryClass>>
                ) {
                    response.body()?.forEach {
                        CountryList.add(CountryClass(it.country_id, it.nicename))
                    }
                    bindingCountryActivity.recyclerViewCountry.adapter = CountryAdapter(CountryList
                        ,this@CountryActivity)
                }

                override fun onFailure(call: Call<List<CountryClass>>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }

    fun fetchUsers(key: String) {
        CountryList.clear()
        if (bindingCountryActivity.searchViewCountry.isNotEmpty()) {
            createClient.getCountry(key)
                .enqueue(object : Callback<List<CountryClass>>, CountryAdapter.onItemClickListener {
                    override fun onResponse(
                        call: Call<List<CountryClass>>,
                        response: Response<List<CountryClass>>
                    ) {
                        response.body()?.forEach {
                            CountryList.add(CountryClass(it.country_id, it.nicename))
                        }
                        bindingCountryActivity.recyclerViewCountry.adapter = CountryAdapter(CountryList
                            ,this@CountryActivity)
                    }

                    override fun onFailure(call: Call<List<CountryClass>>, t: Throwable) {
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
        KEY_COUNTRY_ID = CountryList[position].country_id.toString()
        KEY_COUNTRY_NAME = CountryList[position].nicename
        Toast.makeText(applicationContext,KEY_COUNTRY_NAME,Toast.LENGTH_SHORT).show()
    }
}




