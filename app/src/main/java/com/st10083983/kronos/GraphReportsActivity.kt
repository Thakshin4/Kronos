package com.st10083983.kronos

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class GraphReportsActivity : AppCompatActivity() {

    // on below line we are creating
    // a variable for bar data
    private lateinit var barData: BarData

    // on below line we are creating a
    // variable for bar data set
    private lateinit var barDataSet: BarDataSet

    // on below line we are creating array list for bar data
    private lateinit var barEntriesList: List<BarEntry>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph_report)



        // on below line we are initializing
        // our variable with their ids.
        val barChart: BarChart = findViewById(R.id.idBarChart)

        // on below line we are calling get bar
        // chart data to add data to our array list
        barEntriesList = getBarChartData()

        // on below line we are initializing our bar data set
        barDataSet = BarDataSet(barEntriesList, "Bar Chart Data")

        // on below line we are initializing our bar data
        barData = BarData(barDataSet)

        // on below line we are setting data to our bar chart
        barChart.data = barData

        // on below line we are setting colors for our bar chart text
        barDataSet.valueTextColor = Color.WHITE

        // on below line we are setting color for our bar data set
        barDataSet.color = resources.getColor(R.color.yellow)

        // on below line we are setting text size
        barDataSet.valueTextSize = 16f

        // on below line we are enabling description as false
        barChart.description.isEnabled = false

        // Back Button
        val navBackHome = findViewById<Button>(R.id.graph_reports_back_button)

        navBackHome.setOnClickListener()
        {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun getBarChartData(): MutableList<BarEntry> {
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        val usersRef = FirebaseDatabase.getInstance().getReference("users")
        val last7Days = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)

        val dataPoints = mutableListOf<BarEntry>()

        usersRef.child(uid!!).child("timesheets").orderByChild("date/time")
            .startAt(last7Days.toDouble()).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (timesheetSnapshot in dataSnapshot.children) {
                        val timesheet = timesheetSnapshot.getValue(Timesheet::class.java)
                        timesheet?.let {
                            val date = timesheet.date.time
                            val hoursWorked = timesheet.hoursWorked

                            val daysAgo = ((System.currentTimeMillis() - date) / (24 * 60 * 60 * 1000)).toInt()

                            val dataPoint = BarEntry(daysAgo.toFloat(), hoursWorked.toFloat())
                            dataPoints.add(dataPoint)
                        }
                    }

                    // Process the data points
                    for (dataPoint in dataPoints) {
                        println("Day ${dataPoint.x}: ${dataPoint.y} hours")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // An error occurred while reading the data
                }
            })
        return dataPoints
    }
}