package com.st10083983.kronos

import java.util.Date

data class User(
    val userId: String,
    val name: String,
    val email: String,
    val categories: Map<String, Category>,
    val timesheets: Map<String, Timesheet>
)

data class Category(
    val name: String,
    val maxHours: Int,
    val minHours: Int
){
    // Add a no-argument constructor
    constructor() : this("", 0, 0)
}

data class Timesheet(
    val date: Date,
    val hoursWorked: Int,
    val description: String,
    val categoryName: String
){
    // Add a no-argument constructor
    constructor() : this(Date(0, 0, 0), 0, "", "")
}

data class CategoryReportItems(
    val categoryName: String?,
    val maxHours: Int?,
    val minHours: Int?,
    val totalEntryHoursWorked: Int
)

data class TimesheetReportItems(
    val entryCategory: String,
    val entryDate: String,
    val entryHours: String,
    val entryDescription: String
)

data class DataPoint(
    val x: Int,
    val y: Int
)