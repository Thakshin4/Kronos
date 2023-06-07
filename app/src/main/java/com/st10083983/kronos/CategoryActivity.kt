package com.st10083983.kronos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

// ArrayList of KCategory Objects
var arrCategories = arrayListOf<KCategory>()

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        // Variables
        val edtxCategoryName = findViewById<EditText>(R.id.category_name_input)
        val edtxCategoryMin = findViewById<EditText>(R.id.category_min_input)
        val edtxCategoryMax = findViewById<EditText>(R.id.category_max_input)

        // Create Category
        val createCategory = findViewById<Button>(R.id.create_category_button)

        createCategory.setOnClickListener()
        {
            val categoryName = edtxCategoryName.text.toString()
            val categoryMin = edtxCategoryMin.text.toString()
            val categoryMax = edtxCategoryMax.text.toString()

            // KCategory Object
            val category = KCategory(categoryName, categoryMin, categoryMax)
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
        val existingCategory = arrCategories.find { it.categoryName == category.categoryName }

        if (existingCategory != null)
        {
            Toast.makeText(
                applicationContext,
                "Category with same name already exists",
                Toast.LENGTH_SHORT
            ).show()
        }
        else
        {
            arrCategories.add(category)

            Toast.makeText(
                applicationContext,
                "Category Created",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

data class KCategory(
    val categoryName: String,
    val categoryMin: String,
    val categoryMax: String
)


