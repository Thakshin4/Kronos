package com.st10083983.kronos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

// ArrayList of KCategory Objects
var arrCategories = arrayListOf<KCategory>()

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        // Variables
        val categoryName = findViewById<EditText>(R.id.category_name_input).toString()
        val categoryMin = findViewById<EditText>(R.id.category_min_input).toString()
        val categoryMax = findViewById<EditText>(R.id.category_max_input).toString()

        // KCategory Object
        val category = KCategory(categoryName, categoryMin, categoryMax)

        // Create Category
        val createCategory = findViewById<Button>(R.id.create_category_button)

        createCategory.setOnClickListener()
        {
            handleCreateCategory(category)
        }

        // Back Button
        val navBackHome = findViewById<Button>(R.id.category_back_button)

        navBackHome.setOnClickListener()
        {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Handles Category Creation
    private fun handleCreateCategory(category: KCategory)
    {
        arrCategories.add(category)
    }
}

data class KCategory(
    val categoryName: String,
    val categoryMin: String,
    val categoryMax: String
)


