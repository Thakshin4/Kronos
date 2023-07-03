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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Calendar

// ArrayList of KCategory Objects
var arrCategories = mutableListOf<Category>()
class TimesheetActivity : AppCompatActivity() {

    private lateinit var pickerSelectDate: Button
    private lateinit var textSelectedDate: TextView
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        readCategories()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet)

        // Get the current user's UID
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        // Create a database reference to the "users" node in the database
        val usersRef = FirebaseDatabase.getInstance().getReference("users")

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

        val categoryNames = mutableListOf<String>()

        // Read the user's categories from the database
        usersRef.child(uid!!).child("categories").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (categorySnapshot in dataSnapshot.children) {
                    val category = categorySnapshot.getValue(Category::class.java)
                    category?.let {
                        val categoryName = category.name
                        categoryNames.add(categoryName)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // An error occurred while reading the data
            }
        })

        // Spinner
        val arrCategoryNames = arrCategories.map { it.name }

        val spinnerEntryCategory = findViewById<Spinner>(R.id.entry_category_spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoryNames)
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
            val entryCategoryName = spinnerEntryCategory.selectedItem.toString()

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
                val timesheetEntry = Timesheet(dateFormat.parse(entryDate) as Date, entryHours, entryDescription, entryCategoryName)
                handleCreateEntry(timesheetEntry)
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

    // Handles Entry Creation
    private fun handleCreateEntry(timesheet: Timesheet)
    {
        // Get the current user's UID
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        // Create a database reference to the "users" node in the database
        val usersRef = FirebaseDatabase.getInstance().getReference("users")

        // Write the new category and timesheet to the database under the user's node
        val timesheetKey = usersRef.child(uid!!).child("timesheets").push().key

        val childUpdates = HashMap<String, Any>()
        childUpdates["$uid/timesheets/$timesheetKey"] = timesheet

        usersRef.updateChildren(childUpdates)
            .addOnSuccessListener {
                // Category and timesheet data successfully written to the database
            }
            .addOnFailureListener { e ->
                // An error occurred while writing the data
            }
    }

    // Read Categories
// Read Categories
    private fun readCategories()
    {
        // Get the current user's UID
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        // Create a database reference to the "users" node in the database
        val usersRef = FirebaseDatabase.getInstance().getReference("users")

        // Read the user's categories from the database
        usersRef.child(uid!!).child("categories").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (categorySnapshot in dataSnapshot.children) {
                    val category = categorySnapshot.getValue(Category::class.java)
                    category?.let {
                        arrCategories.add(category)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // An error occurred while reading the data
            }
        })

    }
}
