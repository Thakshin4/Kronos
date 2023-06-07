package com.st10083983.kronos

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

class ReportsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)

        // Back Button
        val navBackHome = findViewById<Button>(R.id.reports_back_button)

        navBackHome.setOnClickListener()
        {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        val selectablePeriods = arrayListOf<String>("7 Days", "14 Days", "30 Days")

        val spinnerSelectablePeriod = findViewById<Spinner>(R.id.selectable_period_spinner)
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

            override fun onNothingSelected(parent: AdapterView<*>?)
            {
                TODO("Not yet implemented")
            }
        }



    }

    fun handleReports(selectablePeriod: String)
    {
        // Reports Logic
        when (selectablePeriod)
        {
            "7 Days" ->
            {
                TODO()
            }
            "14 Days" ->
            {
                TODO()
            }
            "30 Days" ->
            {
                TODO()
            }
        }

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.reports_recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(ItemsViewModel("", 0, ""))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }
}

data class ItemsViewModel(val CategoryName: String, val HoursWorked: Int, val Entries: String)
{
    // CategoryName, HoursWorked, Entries
}