package com.example.adminwavesoffood.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwavesoffood.databinding.ActivityProfileBinding
import com.example.adminwavesoffood.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {
    private val binding: ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser!!.uid

        checkAuthProvider()
        setInfo()

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun checkAuthProvider() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let {
            for (profile in it.providerData) {
                when (profile.providerId) {
                    "google.com" -> {
                        binding.passwordComponent.visibility = View.INVISIBLE
                    }

                    "password" -> {
                        binding.passwordComponent.visibility = View.VISIBLE
                    }
                }
            }
        }
    }


    private fun setInfo() {
        val infoRef = database.reference.child("users/${userId}/user")
        infoRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userModel = snapshot.getValue(UserModel::class.java)
                userModel?.let {
                    binding.apply {
                        nameEditText.setText(it.name)
                        addressEditText.setText(it.location)
                        gmailEditText.setText(it.email)
                        phoneEditText.setText(it.phone)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })


        binding.nameEditText.isEnabled = false
        binding.addressEditText.isEnabled = false
        binding.gmailEditText.isEnabled = false
        binding.phoneEditText.isEnabled = false
        binding.btnSave.isEnabled = false


        editInfoHandler()
    }

    private fun editInfoHandler() {
        var isChecked = false
        binding.checkedView.setOnClickListener {
            isChecked = !isChecked
            binding.nameEditText.isEnabled = true
            binding.addressEditText.isEnabled = true
            binding.gmailEditText.isEnabled = true
            binding.phoneEditText.isEnabled = true
            binding.btnSave.isEnabled = true
            if (isChecked) binding.nameEditText.requestFocus()
        }

        binding.btnSave.setOnClickListener {
            isChecked = false
            binding.nameEditText.isEnabled = false
            binding.addressEditText.isEnabled = false
            binding.gmailEditText.isEnabled = false
            binding.phoneEditText.isEnabled = false
            binding.btnSave.isEnabled = false

            updateUserInfo()
        }
    }

    private fun updateUserInfo() {
        val updatedUser = UserModel(
            name = binding.nameEditText.text.toString(),
            location = binding.addressEditText.text.toString(),
            email = binding.gmailEditText.text.toString(),
            phone = binding.phoneEditText.text.toString(),
        )

        val userRef = database.reference.child("users/$userId/user")
        userRef.setValue(updatedUser)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@ProfileActivity,
                        "User information updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Failed to update user information",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("ProfileActivity", "Error: ${task.exception?.message}")
                }
            }
    }

}