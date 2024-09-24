package com.example.adminwavesoffood.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwavesoffood.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnAddMenu.setOnClickListener {
            val intent = Intent(this, AddMenuActivity::class.java)
            startActivity(intent)
        }

        binding.btnAllItemMenu.setOnClickListener {
            val intent = Intent(this, ListItemActivity::class.java)
            startActivity(intent)
        }
        binding.btnOrderDispatch.setOnClickListener {
            val intent = Intent(this, OrderDispatchActivity::class.java)
            startActivity(intent)
        }
    }
}