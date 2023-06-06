package com.st10083983.kronos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import java.util.Date
import java.text.SimpleDateFormat

// ArrayList of KCategory Objects
var arrEntries = arrayListOf<KTimesheet>()

class TimesheetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet)

        // Spinner Code

        // Variables
        val entryDate = findViewById<EditText>(R.id.entry_date_input).toString()
        val entryHours = findViewById<EditText>(R.id.entry_hours_input).toString().toInt()
        val entryDescription = findViewById<EditText>(R.id.entry_description_input).toString()
        val entryCategory = findViewById<Spinner>(R.id.entry_category_input).toString() // Store from Spinner

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        // KTimesheet Object
        val timesheetEntry = KTimesheet(dateFormat.parse(entryDate), entryHours, entryDescription, entryCategory)

        // Create Timesheet Entry
        val createTimesheetEntry = findViewById<Button>(R.id.create_entry_button)

        createTimesheetEntry.setOnClickListener()
        {
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

        // Spinner Code
        // Search ArrayList for Specific Category
        val searchCategory = "" // Pick from Spinner
        val foundCategory = arrCategories.find { it.categoryName == searchCategory }
    }

    private fun handleEntryCreation(timesheetEntry:KTimesheet)
    {
        arrEntries.add(timesheetEntry)
    }
}

data class KTimesheet(
    val entryDate: Date,
    val entryHours: Int,
    val entryDescription: String,
    val entryCategory: String
)