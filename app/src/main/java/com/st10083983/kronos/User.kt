package com.st10083983.kronos

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
    val date: String,
    val hoursWorked: Int,
    val description: String,
    val categoryName: String
)
