package com.example.adminwavesoffood.activities

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwavesoffood.databinding.ActivitySignUpBinding
import com.example.adminwavesoffood.models.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var name: String
    private lateinit var password: String
    private lateinit var email: String
    private lateinit var nameOfRestaurant: String
    private lateinit var location: String
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // initialize Firebase authentication
        auth = Firebase.auth
        // initialize Firebase database
        database = Firebase.database.reference

        setOnClickSignUpBtn()
        setOnClickLoginTextView()
        setOnCompletedLocationList()
    }

    private fun setOnCompletedLocationList() {
        val locationList = arrayOf("Tokyo", "New York", "Ho Chi Minh", "Bang Kok")
        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setOnClickListener {
            autoCompleteTextView.showDropDown()
        }
    }

    private fun setOnClickSignUpBtn() {
        binding.signUpBtn.setOnClickListener {
            name = binding.editTextNameOwner.text.toString().trim()
            nameOfRestaurant = binding.editTextNameRestaurant.text.toString().trim()
            email = binding.editTextEmailAddress.text.toString().trim()
            password = binding.editTextPassword.text.toString().trim()
            location = binding.listOfLocation.text.toString()

            if (email.isBlank() || password.isBlank() || name.isBlank() || nameOfRestaurant.isBlank() || location.isBlank()) {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, password)
            }

        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    this,
                    "Successfully created", Toast.LENGTH_SHORT
                ).show()

                saveUserData()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Failed to create account: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun saveUserData() {
        name = binding.editTextNameOwner.text.toString().trim()
        nameOfRestaurant = binding.editTextNameRestaurant.text.toString().trim()
        email = binding.editTextEmailAddress.text.toString().trim()
        password = binding.editTextPassword.text.toString().trim()
        location = binding.listOfLocation.text.toString()

        val user = UserModel(name, nameOfRestaurant, email, password, location)
        val userId: String = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("users").child("$userId/user/").setValue(user)
    }

    private fun setOnClickLoginTextView() {
        binding.loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}