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
)

data class Timesheet(
    val date: Date,
    val hoursWorked: Int,
    val description: String,
    val categoryName: String
)

data class CategoryReportItems(
    val categoryName: String,
    val totalEntryHoursWorked: Int
)

data class TimesheetReportItems(
    val entryCategory: String,
    val entryDate: String,
    val entryHours: String,
    val entryDescription: String
)