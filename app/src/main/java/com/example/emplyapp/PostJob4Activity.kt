package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isNotEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emplyapp.databinding.ActivityPostJob4Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostJob4Activity : AppCompatActivity(), CategoryAdapter.onItemClickListener {
    private lateinit var bindingPostJob4: ActivityPostJob4Binding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    var KEY_CATEGORY_ID: String? = null
    var KEY_CATEGORY_NAME: String? = null
    var KEY_USERNAME: String? = null

    private var CategoryList = ArrayList<CategoryClass>()
    val categoryClient = CategoryAPI.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingPostJob4 = ActivityPostJob4Binding.inflate(layoutInflater)
        setContentView(bindingPostJob4.root)

        recyclerView = bindingPostJob4.recyclerViewCategory
        searchView = bindingPostJob4.searchViewPerksNBenefits
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        bindingPostJob4.btnContinue.setOnClickListener {
            if(KEY_CATEGORY_ID != null && KEY_CATEGORY_NAME != null && KEY_USERNAME != null) {
                val i = Intent(applicationContext,PostJob5Activity::class.java)
                i.putExtra("COUNTRY_ID" , KEY_CATEGORY_ID)
                i.putExtra("COUNTRY_NAME" , KEY_CATEGORY_NAME)
                startActivity(i)
            } else {
                Toast.makeText(applicationContext,"Please select your country",Toast.LENGTH_LONG).show()
            }
        }

        bindingPostJob4.txtCheckValue.text = KEY_CATEGORY_ID+" "+KEY_CATEGORY_NAME+" "+KEY_USERNAME
    }

    override fun onResume() {
        super.onResume()
        callCategoryData()
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

    fun callCategoryData() {
        CategoryList.clear()
        categoryClient.getAllCategory()
            .enqueue(object : retrofit2.Callback<List<CategoryClass>> {
                override fun onResponse(
                    call: Call<List<CategoryClass>>,
                    response: Response<List<CategoryClass>>
                ) {
                    response.body()?.forEach {
                        CategoryList.add(CategoryClass(it.category_name, it.detail))
                    }
                    bindingPostJob4.recyclerViewCategory.adapter = CategoryAdapter(CategoryList
                        ,this@PostJob4Activity)
                }

                override fun onFailure(call: Call<List<CategoryClass>>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }

    fun fetchUsers(key: String) {
        CategoryList.clear()
        if (bindingPostJob4.searchViewPerksNBenefits.isNotEmpty()) {
            categoryClient.getCategory(key)
                .enqueue(object : Callback<List<CategoryClass>>, CategoryAdapter.onItemClickListener {
                    override fun onResponse(
                        call: Call<List<CategoryClass>>,
                        response: Response<List<CategoryClass>>
                    ) {
                        response.body()?.forEach {
                            CategoryList.add(CategoryClass(it.category_name, it.detail))
                        }
                        bindingPostJob4.recyclerViewCategory.adapter = CategoryAdapter(CategoryList
                            ,this@PostJob4Activity)
                    }

                    override fun onFailure(call: Call<List<CategoryClass>>, t: Throwable) {
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
        KEY_CATEGORY_ID = CategoryList[position].category_name.toString()
        KEY_CATEGORY_NAME = CategoryList[position].detail
        Toast.makeText(applicationContext,KEY_CATEGORY_NAME,Toast.LENGTH_SHORT).show()
        bindingPostJob4.txtCheckValue.text = KEY_CATEGORY_ID+" "+KEY_CATEGORY_NAME+" "+KEY_USERNAME
    }
}