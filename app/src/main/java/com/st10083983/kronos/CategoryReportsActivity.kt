package com.st10083983.kronos

import CategoryCustomAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


val arrCategoryReportItems = arrayListOf<CategoryReportItems>()

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
        val data = ArrayList<CategoryReportItems>()
        data.clear() // Clear RecyclerView Test

        for (item in arrCategoryReportItems)
        {
            data.add(item)
        }

        // This will pass the ArrayList to our Adapter
        var adapter = CategoryCustomAdapter(data)
        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }

    private fun handleReportItems()
    {
        // Get the current user's UID
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        // Create a database reference to the "users" node in the database
        val usersRef = FirebaseDatabase.getInstance().getReference("users")

        // Read the user's categories and timesheets from the database
        usersRef.child(uid!!).addListenerForSingleValueEvent(object : ValueEventListener
        {
            override fun onDataChange(dataSnapshot: DataSnapshot)
            {

                val categoriesSnapshot = dataSnapshot.child("categories")
                val timesheetsSnapshot = dataSnapshot.child("timesheets")
                arrCategoryReportItems.clear()

                // Calculate total hours worked for each category
                for (categorySnapshot in categoriesSnapshot.children)
                {
                    val categoryId = categorySnapshot.key
                    val categoryName = categorySnapshot.child("name").getValue(String::class.java)
                    val maxHours = categorySnapshot.child("maxHours").getValue(Int::class.java)
                    val minHours = categorySnapshot.child("minHours").getValue(Int::class.java)

                    var totalHoursWorked = 0

                    for (timesheetSnapshot in timesheetsSnapshot.children) {
                        if (timesheetSnapshot.child("categoryName").getValue(String::class.java) == categoryName) {
                            val hoursWorked = timesheetSnapshot.child("hoursWorked").getValue(Int::class.java)
                            totalHoursWorked += hoursWorked ?: 0
                        }
                    }

                    // Store the total hours worked for the category
                    arrCategoryReportItems.add(CategoryReportItems(categoryName, maxHours, minHours, totalHoursWorked))
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // An error occurred while reading the data
            }
        })
    }
}
