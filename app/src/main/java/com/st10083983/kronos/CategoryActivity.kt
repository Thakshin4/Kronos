package com.st10083983.kronos

import  android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class CategoryActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    // Get the current user's UID
    private val uid = FirebaseAuth.getInstance().currentUser?.uid

    // Create a database reference to the "users" node in the database
    private val usersRef = FirebaseDatabase.getInstance().getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        database = FirebaseDatabase.getInstance().reference

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

            if (categoryName.isEmpty() || categoryMin.isEmpty() || categoryMax.isEmpty())
            {
                Toast.makeText(
                    applicationContext,
                    "Certain fields are Empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else
            {
                val category = Category(categoryName, categoryMin.toInt(), categoryMax.toInt())
                handleCreateCategory(category)
            }
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
    private fun handleCreateCategory(category: Category)
    {
        // Get the current user's UID
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        // Create a database reference to the "users" node in the database
        val usersRef = FirebaseDatabase.getInstance().getReference("users")

        // Write the new category and timesheet to the database under the user's node
        val categoryKey = usersRef.child(uid!!).child("categories").push().key

        val childUpdates = HashMap<String, Any>()
        childUpdates["$uid/categories/$categoryKey"] = category

        usersRef.updateChildren(childUpdates)
            .addOnSuccessListener {
                // Category and timesheet data successfully written to the database
            }
            .addOnFailureListener { e ->
                // An error occurred while writing the data
            }
    }
}