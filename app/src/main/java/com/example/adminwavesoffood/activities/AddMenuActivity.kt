package com.example.adminwavesoffood.activities

import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwavesoffood.databinding.ActivityAddMenuBinding

class AddMenuActivity : AppCompatActivity() {
    private val binding: ActivityAddMenuBinding by lazy {
        ActivityAddMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pickImage =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    binding.pickImage.setImageURI(uri)
                }
            }
        binding.pickImage.setOnClickListener {
            pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}