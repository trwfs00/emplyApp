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
    private lateinit var bindingPJ4: ActivityPostJob4Binding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    var KEY_DESCRIPTION :String? = null
    var KEY_QUALIFICATIONS :String? = null
    var KEY_PAB :String? = null

    var KEY_NAME :String? = null
    var KEY_CATEGORY_ID: Int? = null
    var KEY_CATEGORY_NAME: String? = null

    private var CategoryList = ArrayList<CategoryClass>()
    val categoryClient = CategoryAPI.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingPJ4 = ActivityPostJob4Binding.inflate(layoutInflater)
        setContentView(bindingPJ4.root)

        var intent: Intent = getIntent()
        KEY_NAME = intent.getStringExtra("NAME")
        KEY_DESCRIPTION = intent.getStringExtra("DESCRIPTION")
        KEY_QUALIFICATIONS = intent.getStringExtra("QUALIFICATIONS")
        KEY_PAB = intent.getStringExtra("PAB")
        bindingPJ4.txtCheckValue.text = KEY_NAME+" "+KEY_DESCRIPTION+" "+KEY_QUALIFICATIONS+" "+KEY_PAB

        recyclerView = bindingPJ4.recyclerViewCategory
        searchView = bindingPJ4.searchViewPerksNBenefits
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        bindingPJ4.btnContinue.setOnClickListener {
            if(KEY_CATEGORY_ID != null && KEY_CATEGORY_NAME != null) {
                val i = Intent(applicationContext,PostJob5Activity::class.java)
                i.putExtra("CATEGORY_ID" , KEY_CATEGORY_ID.toString().toInt())
                i.putExtra("CATEGORY_NAME" , KEY_CATEGORY_NAME)
                i.putExtra("NAME" , KEY_NAME)
                i.putExtra("DESCRIPTION" , KEY_DESCRIPTION)
                i.putExtra("QUALIFICATIONS" , KEY_QUALIFICATIONS)
                i.putExtra("PAB" , KEY_PAB)
                startActivity(i)
            } else {
                Toast.makeText(applicationContext,"Please select your country",Toast.LENGTH_LONG).show()
            }
        }
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
                if (newText.isEmpty()) {
                    callCategoryData()
                } else {
                    fetchUsers(newText)
                }
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
                        CategoryList.add(CategoryClass(it.category_id, it.category_name, it.detail))
                    }
                    bindingPJ4.recyclerViewCategory.adapter = CategoryAdapter(CategoryList
                        ,this@PostJob4Activity)
                }

                override fun onFailure(call: Call<List<CategoryClass>>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }

    fun fetchUsers(key: String) {
        CategoryList.clear()
        if (bindingPJ4.searchViewPerksNBenefits.isNotEmpty()) {
            categoryClient.getCategoryKey(key)
                .enqueue(object : Callback<List<CategoryClass>>, CategoryAdapter.onItemClickListener {
                    override fun onResponse(
                        call: Call<List<CategoryClass>>,
                        response: Response<List<CategoryClass>>
                    ) {
                        response.body()?.forEach {
                            CategoryList.add(CategoryClass(it.category_id, it.category_name, it.detail))
                        }
                        bindingPJ4.recyclerViewCategory.adapter = CategoryAdapter(CategoryList
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
        KEY_CATEGORY_ID = CategoryList[position].category_id.toString().toInt()
        KEY_CATEGORY_NAME = CategoryList[position].category_name
        Toast.makeText(applicationContext,KEY_CATEGORY_NAME,Toast.LENGTH_SHORT).show()
    }
}