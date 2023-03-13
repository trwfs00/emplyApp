package com.example.emplyapp
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Year
import java.util.Calendar
class DatePickerFragment: DialogFragment(),
    DatePickerDialog.OnDateSetListener {
    private lateinit var calendar: Calendar
    @SuppressLint("ResourceType")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireContext(), 4, this, year, month, day)
    }
    override fun onCancel(dialog: DialogInterface) {
        Toast.makeText(activity, "Please select a date.", Toast.LENGTH_LONG).show()
        super.onCancel(dialog)
    }
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        var tv: TextView? = activity?.findViewById(R.id.edtDateBirthday)
        tv!!.text = formatDate(year, month, day)
    }
    private fun formatDate(year: Int, month: Int, day: Int): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        var calendar: Calendar = Calendar.getInstance();
        calendar.set(year, month, day)
        val chosenDate = calendar.time
        val df = dateFormat.format(chosenDate)
        return (df)
    }
}

