package com.example.emplyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
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

    var type: String = ""
    var CurrencyItem: String = ""

    private var CurrencyList = ArrayList<CurrencyClass>()
    val createClient = CurrencyAPI.create()
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
        var emp_id : Int = session.pref.getString(SessionManager.KEY_ID, null)!!.toInt()

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

    fun addJobs() {
        var emp_id : Int = session.pref.getString(SessionManager.KEY_ID, null)!!.toInt()
        Toast.makeText(applicationContext, "ไม่เข้าInsert", Toast.LENGTH_SHORT).show()
        insertClient.insertJobs(
            job_name = KEY_NAME.toString(),
            salaryFrom = bindingPJ5.edtMin.text.toString().toInt(),
            salaryTo = bindingPJ5.edtMax.text.toString().toInt(),
            description = KEY_DESCRIPTION.toString(),
            minimumQualification = KEY_QUALIFICATIONS.toString(),
            type = type,
            category_id = KEY_CATEGORY_ID.toString().toInt(),
            employer_id = emp_id,
            benefit = KEY_PAB.toString(),
            code = CurrencyItem
            ).enqueue(object : Callback<JobsClass> {

            override fun onResponse(call: Call<JobsClass>, response: Response<JobsClass>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "Successfully Register", Toast.LENGTH_LONG).show()
                    bindingPJ5.txtCheckValue.text = KEY_NAME+" "+bindingPJ5.edtMin.text.toString().toInt()+" "+bindingPJ5.edtMax.text.toString().toInt()+" "+KEY_DESCRIPTION+" "+KEY_QUALIFICATIONS+" "+type+" "+KEY_CATEGORY_ID+" "+emp_id+" "+KEY_PAB+" "+CurrencyItem
                } else {
                    Toast.makeText(applicationContext, "เข้า Insert เข้า else", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JobsClass>, t: Throwable) {
                Toast.makeText(applicationContext, "Error onFailure: " + t.message, Toast.LENGTH_LONG).show()
                bindingPJ5.txtCheckValue.text = KEY_NAME+" "+bindingPJ5.edtMin.text.toString().toInt()+" "+bindingPJ5.edtMax.text.toString().toInt()+" "+KEY_DESCRIPTION+" "+KEY_QUALIFICATIONS+" "+type+" "+KEY_CATEGORY_ID+" "+emp_id+" "+KEY_PAB+" "+CurrencyItem
            }
        })
    }
}