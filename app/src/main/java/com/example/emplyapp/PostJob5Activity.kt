package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.emplyapp.databinding.ActivityPostJob5Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostJob5Activity : AppCompatActivity() {
    private lateinit var bindingPJ5 : ActivityPostJob5Binding
    lateinit var session: SessionManager

    var KEY_NAME :String? = null
    var KEY_DESCRIPTION :String? = null
    var KEY_QUALIFICATIONS :String? = null
    var KEY_PAB :String? = null

    var KEY_CATEGORY_ID: Int? = null
    var KEY_CATEGORY_NAME: String? = null

    var KEY_LOGIN_ID : Int? = null
    var KEY_ROLE : Int? = null
    var KEY_USERNAME : String? = null
    var KEY_FULLNAME : String? = null
    var KEY_EMAIL : String? = null
    var KEY_PHONE : String? = null
    var KEY_BIRTHDAY : String? = null
    var KEY_NICKNAME : String? = null
    var KEY_GENDER : String? = null
    var KEY_COUNTRY_ID : Int? = null
    var KEY_PICTURE : String? = null
    var KEY_EMPCARD : String? = null
    var KEY_DEPT : String? = null
    var KEY_COMPANY_ID : Int? = null
    var KEY_STATUS : Int? = null
    var KEY_EMPLOYER_ID : Int? = null

    var type: String = ""
    var CurrencyItem: String = ""

    private var CurrencyList = ArrayList<CurrencyClass>()
    val createClient = CurrencyAPI.create()
    val createUserData = UserAPI.create()
    val insertClient = UserAPI.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingPJ5 = ActivityPostJob5Binding.inflate(layoutInflater)
        setContentView(bindingPJ5.root)

        var intent: Intent = getIntent()
        KEY_NAME = intent.getStringExtra("NAME")
        KEY_DESCRIPTION = intent.getStringExtra("DESCRIPTION")
        KEY_QUALIFICATIONS = intent.getStringExtra("QUALIFICATIONS")
        KEY_PAB = intent.getStringExtra("PAB")
        KEY_CATEGORY_ID = intent.getIntExtra("CATEGORY_ID", 0)
        KEY_CATEGORY_NAME = intent.getStringExtra("CATEGORY_NAME")

        showDropdown()
        session = SessionManager(applicationContext)
        KEY_USERNAME = session.pref.getString(SessionManager.KEY_USERNAME, null).toString()
        getUserData(KEY_USERNAME.toString())

        bindingPJ5.btnPost.setOnClickListener {
            addListenerOnButton()
            addJobs()
        }
    }

    fun addListenerOnButton() {
        var selectID: Int = bindingPJ5.radioGType.checkedRadioButtonId
        var radioButtonChecked: RadioButton = findViewById(selectID)

        type = radioButtonChecked.text as String
    }

    private fun showDropdown(){
        CurrencyList.clear()
        createClient.getAllCurrency()
            .enqueue(object : Callback<List<CurrencyClass>> {
            override fun onResponse(call: Call<List<CurrencyClass>>, response: Response<List<CurrencyClass>>){
                response.body()?.forEach{
                    CurrencyList.add(CurrencyClass(it.code,it.description))
                }
                val sub = CurrencyList.map { it.code }.toTypedArray()
                val arrayAdapter = ArrayAdapter(this@PostJob5Activity,R.layout.currency_dropdown_item,sub)
                bindingPJ5.AutoCompleteTextView.setAdapter(arrayAdapter)
                bindingPJ5.AutoCompleteTextView.setOnItemClickListener{ parent,_,position,_ ->
                    CurrencyItem = parent.getItemAtPosition(position) as String
                }
            }
            override fun onFailure(call: Call<List<CurrencyClass>>, t:Throwable){
                Toast.makeText(applicationContext,"Error onFailure"+ t.message, Toast.LENGTH_LONG).show()
            }
        })
        bindingPJ5.AutoCompleteTextView.setText("Currency")

    }
    private fun getUserData(msg: String) {
        createUserData.fetchUserData(
            username = msg
        ).enqueue(object : Callback<UserDataClass> {
            override fun onResponse(call: Call<UserDataClass>, response: Response<UserDataClass>) {
                if(response.isSuccessful) {
                    KEY_LOGIN_ID = response.body()?.Login_id
                    KEY_ROLE = response.body()?.Login_role
                    KEY_FULLNAME = response.body()?.fullName
                    KEY_EMAIL = response.body()?.email
                    KEY_PHONE = response.body()?.phone
                    KEY_BIRTHDAY = response.body()?.birthday
                    KEY_NICKNAME = response.body()?.nickName
                    KEY_GENDER = if(response.body()?.gender == 0) "Male" else "Female"
                    KEY_COUNTRY_ID = response.body()?.country_id
                    KEY_PICTURE = response.body()?.picture_emp
                    KEY_EMPCARD = response.body()?.empcard
                    KEY_DEPT = response.body()?.dept
                    KEY_COMPANY_ID = response.body()?.company_id
                    KEY_STATUS = response.body()?.status
                    KEY_EMPLOYER_ID = response.body()?.employer_id
                } else {
                    Toast.makeText(applicationContext,"Failure on calling user data...", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<UserDataClass>, t: Throwable) {
                Toast.makeText(applicationContext,"Failed on "+t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun addJobs() {
        getUserData(KEY_USERNAME.toString())
        insertClient.insertJobs(
            job_name = KEY_NAME.toString(),
            salaryFrom = bindingPJ5.edtMin.text.toString().toInt(),
            salaryTo = bindingPJ5.edtMax.text.toString().toInt(),
            description = KEY_DESCRIPTION.toString(),
            minimumQualification = KEY_QUALIFICATIONS.toString(),
            type = type,
            category_id = KEY_CATEGORY_ID.toString().toInt(),
            employer_id = KEY_EMPLOYER_ID!!.toInt(),
            benefit = KEY_PAB.toString(),
            code = CurrencyItem
            ).enqueue(object : Callback<JobsClass> {

            override fun onResponse(call: Call<JobsClass>, response: Response<JobsClass>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "Successfully Register", Toast.LENGTH_LONG).show()
                    var i: Intent = Intent(applicationContext, EmployerHomeActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Failed on posting job..", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JobsClass>, t: Throwable) {
                Toast.makeText(applicationContext, "Error onFailure: " + t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}