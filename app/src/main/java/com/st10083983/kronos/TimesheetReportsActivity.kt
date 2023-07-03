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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

val arrEntryReportItems = arrayListOf<TimesheetReportItems>()

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

        // Spinner
        //______________________Code Attributon_____________________________
        // The following code was taken from GeeksforGeeks:
        // Author : Praveenruhil
        // Link : https://www.geeksforgeeks.org/spinner-in-kotlin/
        val selectablePeriods = arrayListOf("7 Days", "14 Days", "30 Days")

        val spinnerSelectablePeriod = findViewById<Spinner>(R.id.timesheet_selectable_period_spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, selectablePeriods)
        // __________________________________________________________________
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
                    "You have selected the following period: $selectablePeriods",
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
        val recyclerview = findViewById<RecyclerView>(R.id.timesheet_reports_recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<TimesheetReportItems>()
        data.clear() // Clear RecyclerView Test

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
        // Get the current user's UID
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        // Create a database reference to the "users" node in the database
        val usersRef = FirebaseDatabase.getInstance().getReference("users")

        // Read the user's timesheets from the database
        usersRef.child(uid!!).child("timesheets").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val timesheets = mutableListOf<Timesheet>()
                arrEntryReportItems.clear()

                for (timesheetSnapshot in dataSnapshot.children) {
                    val timesheet = timesheetSnapshot.getValue(Timesheet::class.java)
                    timesheet?.let {
                        timesheets.add(timesheet)
                    }
                }

                // Process the timesheets
                for (timesheet in timesheets) {
                    println("Date: ${timesheet.date}")
                    println("Hours Worked: ${timesheet.hoursWorked}")
                    println("Description: ${timesheet.description}")
                    println("Category Name: ${timesheet.categoryName}")
                    println()

                    arrEntryReportItems.add(TimesheetReportItems(timesheet.categoryName, timesheet.date.toString(), timesheet.hoursWorked.toString(), timesheet.description))
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // An error occurred while reading the data
            }
        })
    }
}


