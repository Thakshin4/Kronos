package com.st10083983.kronos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Navigate to Category
        val navCategory = findViewById<Button>(R.id.nav_category_button)

        navCategory.setOnClickListener()
        {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Navigate to Timesheet
        val navTimesheet = findViewById<Button>(R.id.nav_timesheet_button)

        navTimesheet.setOnClickListener()
        {
            val intent = Intent(this, TimesheetActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Navigate to Category Reports
        val navCategoryReports = findViewById<Button>(R.id.nav_category_reports_button)

        navCategoryReports.setOnClickListener()
        {
            val intent = Intent(this, CategoryReportsActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Navigate to Category Reports
        val navEntryReports = findViewById<Button>(R.id.nav_timesheet_reports_button)

        navEntryReports.setOnClickListener()
        {
            val intent = Intent(this, TimesheetReportsActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Navigate to Timer
        val navTimer = findViewById<Button>(R.id.nav_timer_button)

        navTimer.setOnClickListener()
        {
            val intent = Intent(this, TimerActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}