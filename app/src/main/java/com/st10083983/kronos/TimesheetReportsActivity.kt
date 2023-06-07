package com.st10083983.kronos

import EntryCustomAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar
import java.util.Date

val arrEntryReportItems = arrayListOf<KEntryReportItems>()

class TimesheetReportsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet_reports)

        // Back Button
        val navBackHome = findViewById<Button>(R.id.timesheet_reports_back_button)

        navBackHome.setOnClickListener()
        {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        val selectablePeriods = arrayListOf<String>("7 Days", "14 Days", "30 Days")

        val spinnerSelectablePeriod = findViewById<Spinner>(R.id.timesheet_selectable_period_spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, selectablePeriods)
        spinnerSelectablePeriod.adapter = adapter

        spinnerSelectablePeriod.adapter = adapter
        spinnerSelectablePeriod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            )
            {
                // Test Text
                Toast.makeText(
                    applicationContext,
                    "You have selected the following category: " + selectablePeriods,
                    Toast.LENGTH_SHORT
                ).show()
                handleReports(spinnerSelectablePeriod.selectedItem.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    fun handleReports(selectablePeriod: String)
    {
        val calendar = Calendar.getInstance()
        var period = calendar.time

        // Reports Logic
        when (selectablePeriod)
        {
            "7 Days" ->
            {
                calendar.add(Calendar.DAY_OF_YEAR, -7)
                period = calendar.time
            }
            "14 Days" ->
            {
                calendar.add(Calendar.DAY_OF_YEAR, -14)
                period = calendar.time
            }
            "30 Days" ->
            {
                calendar.add(Calendar.DAY_OF_YEAR, -30)
                period = calendar.time
            }
        }

        handleReportItems(period)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.reports_recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<KEntryReportItems>()

        for (item in arrEntryReportItems)
        {
            data.add(item)
        }

        // This will pass the ArrayList to our Adapter
        val adapter = EntryCustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }

    private fun handleReportItems(period: Date)
    {
        var stringEntry = ""
        for (entry in arrEntries)
        {
            stringEntry = "Date: " + entry.entryDate + "\n" + "Hours Worked: " + entry.entryHours + "\n" + "Description: " + entry.entryDescription
        }

        arrEntryReportItems.add(KEntryReportItems(stringEntry))
    }
}

data class KEntryReportItems(
    val entry: String,
)