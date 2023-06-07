package com.st10083983.kronos

import CategoryCustomAdapter
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

val arrCategoryReportItems = arrayListOf<KCategoryReportItems>()

class CategoryReportsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_reports)

        // Back Button
        val navBackHome = findViewById<Button>(R.id.category_reports_back_button)

        navBackHome.setOnClickListener()
        {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        handleReports()
    }

    private fun handleReports()
    {
        handleReportItems()

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.reports_recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<KCategoryReportItems>()
        data.clear() // Clear RecyclerView Test

        for (item in arrCategoryReportItems)
        {
            data.add(item)
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CategoryCustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }

    private fun handleReportItems()
    {
        for (category in arrCategories)
        {
            var totalHoursWorked = 0
            val arrEntriesInCategory = arrayListOf<KTimesheet>()

            for (entry in arrEntries)
            {
                if (category.categoryName == entry.entryCategory)
                {
                    arrEntriesInCategory.add(entry)
                    totalHoursWorked += entry.entryHours
                }
            }
            arrCategoryReportItems.add(KCategoryReportItems(category.categoryName, totalHoursWorked))
        }
    }
}

data class KCategoryReportItems(
    val categoryName: String,
    val totalEntryHoursWorked: Int
)
