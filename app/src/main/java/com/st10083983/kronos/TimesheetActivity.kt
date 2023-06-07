package com.st10083983.kronos

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
    //Variables for DatePicker
    lateinit var pickDateBtn: Button
    lateinit var selectedDateTV: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet)

        //Code For Date Picker
        //______________________Code Attributon_____________________________
        // The following code was taken from GeeksforGeeks:
        // Author : chaitanyamunje
        // Link : https://www.geeksforgeeks.org/datepicker-in-android/
        pickDateBtn = findViewById(R.id.idBtnPickDate)
        selectedDateTV = findViewById(R.id.idTVSelectedDate)
        pickDateBtn.setOnClickListener {
            val c = Calendar.getInstance()
            // Gets day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            // Sets variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // Formats Date.
                    selectedDateTV.text =
                        (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        //__________________________________________________________________

        // Spinner
        //______________________Code Attributon_____________________________
        // The following code was taken from GeeksforGeeks:
        // Author : Praveenruhil
        // Link : https://www.geeksforgeeks.org/spinner-in-kotlin/
        val arrCategoryNames = arrCategories.map { it.categoryName }
        val spinnerEntryCategory = findViewById<Spinner>(R.id.entry_category_spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrCategoryNames)
        spinnerEntryCategory.adapter = adapter
        //__________________________________________________________________

        // Variables
        val edtxEntryDate = findViewById<EditText>(R.id.entry_date_input)
        val edtxEntryHours = findViewById<EditText>(R.id.entry_hours_input)
        val edtxEntryDescription = findViewById<EditText>(R.id.entry_description_input)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        // Create Timesheet Entry
        val createTimesheetEntry = findViewById<Button>(R.id.create_entry_button)

        createTimesheetEntry.setOnClickListener()
        {
            val entryDate = edtxEntryDate.text.toString()
            val entryHours = edtxEntryHours.text.toString().toInt()
            val entryDescription = edtxEntryDescription.text.toString()
            val entryCategory = spinnerEntryCategory.selectedItem.toString()

            // KTimesheet Object
            val timesheetEntry = KTimesheet(dateFormat.parse(entryDate) as Date, entryHours, entryDescription, entryCategory)
            handleEntryCreation(timesheetEntry)
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