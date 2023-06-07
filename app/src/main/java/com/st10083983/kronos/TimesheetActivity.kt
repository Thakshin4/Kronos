package com.st10083983.kronos

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Calendar

// ArrayList of KTimesheet Objects
var arrEntries = arrayListOf<KTimesheet>()

class TimesheetActivity : AppCompatActivity() {


    private lateinit var pickerSelectDate: Button
    private lateinit var textSelectedDate: TextView
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet)

        // Date Picker
        pickerSelectDate = findViewById(R.id.pick_date_button)
        textSelectedDate = findViewById(R.id.entry_date_input)

        pickerSelectDate.setOnClickListener()
        {

            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth ->
                    textSelectedDate.text = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                }, year, month, day
            )
            datePickerDialog.show()
        }

        // Spinner
        val arrCategoryNames = arrCategories.map { it.categoryName }

        val spinnerEntryCategory = findViewById<Spinner>(R.id.entry_category_spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrCategoryNames)
        spinnerEntryCategory.adapter = adapter

        // Variables
        val edtxEntryHours = findViewById<EditText>(R.id.entry_hours_input)
        val edtxEntryDescription = findViewById<EditText>(R.id.entry_description_input)

        val dateFormat = SimpleDateFormat("dd-mm-yyyy")

        // Create Timesheet Entry
        val createTimesheetEntry = findViewById<Button>(R.id.create_entry_button)

        createTimesheetEntry.setOnClickListener()
        {
            val entryDate = textSelectedDate.text.toString()
            val entryHours = edtxEntryHours.text.toString().toInt()
            val entryDescription = edtxEntryDescription.text.toString()
            val entryCategory = spinnerEntryCategory.selectedItem.toString()

            if (entryDate.isEmpty() || entryDescription.isEmpty())
            {
                Toast.makeText(
                    applicationContext,
                    "Certain fields are Empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else
            {
                val timesheetEntry = KTimesheet(dateFormat.parse(entryDate) as Date, entryHours, entryDescription, entryCategory)
                handleEntryCreation(timesheetEntry)
            }
        }

        // Back Button
        val navBackHome = findViewById<Button>(R.id.timesheet_back_button)

        navBackHome.setOnClickListener()
        {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun handleEntryCreation(timesheetEntry:KTimesheet)
    {
        arrEntries.add(timesheetEntry)

        Toast.makeText(
            applicationContext,
            "Timesheet Entry Created",
            Toast.LENGTH_SHORT
        ).show()
    }
}

data class KTimesheet(
    val entryDate: Date,
    val entryHours: Int,
    val entryDescription: String,
    val entryCategory: String
)