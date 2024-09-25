package com.example.adminwavesoffood.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwavesoffood.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private val binding: ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.nameEditText.isEnabled = false
        binding.addressEditText.isEnabled = false
        binding.gmailEditText.isEnabled = false
        binding.phoneEditText.isEnabled = false
        binding.passwordEditText.isEnabled = false
        binding.btnSave.isEnabled = false


        var isChecked = false
        binding.checkedView.setOnClickListener {
            isChecked = !isChecked
            binding.nameEditText.isEnabled = true
            binding.addressEditText.isEnabled = true
            binding.gmailEditText.isEnabled = true
            binding.phoneEditText.isEnabled = true
            binding.passwordEditText.isEnabled = true
            binding.btnSave.isEnabled = true
            if (isChecked) binding.nameEditText.requestFocus()
        }

        binding.btnSave.setOnClickListener {
            isChecked = false
            binding.nameEditText.isEnabled = false
            binding.addressEditText.isEnabled = false
            binding.gmailEditText.isEnabled = false
            binding.phoneEditText.isEnabled = false
            binding.passwordEditText.isEnabled = false
            binding.btnSave.isEnabled = false
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}