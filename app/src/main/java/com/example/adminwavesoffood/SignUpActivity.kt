package com.example.adminwavesoffood

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwavesoffood.databinding.ActivitySignUpBinding

class SignUpActivity: AppCompatActivity() {
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickSignUpBtn()
        setOnClickLoginTextView()
        setOnCompletedLocationList()
    }

    private fun setOnCompletedLocationList() {
        val locationList = arrayOf("Tokyo", "New York", "Ho Chi Minh", "Bang Kok")
        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun setOnClickSignUpBtn() {
        binding.signUpBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun setOnClickLoginTextView() {
        binding.loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}