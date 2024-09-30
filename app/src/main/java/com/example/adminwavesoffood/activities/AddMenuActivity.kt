package com.example.adminwavesoffood.activities

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.adminwavesoffood.databinding.ActivityAddMenuBinding
import com.example.adminwavesoffood.models.BaseModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddMenuActivity : AppCompatActivity() {
    private val binding: ActivityAddMenuBinding by lazy {
        ActivityAddMenuBinding.inflate(layoutInflater)
    }

    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private var foodImage: Uri? = null
    private lateinit var foodDescription: String
    private lateinit var foodIngredients: String

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.addItemBtn.setOnClickListener {
            foodName = binding.editTextName.text.toString()
            foodPrice = binding.editTextPrice.text.toString()
            foodDescription = binding.editTextDescription.text.toString()
            foodIngredients = binding.editTextIngredient.text.toString()

            if (!(foodName.isBlank() || foodPrice.isBlank() || foodDescription.isBlank() || foodIngredients.isBlank())) {
                uploadData()
                Toast.makeText(this, "Item added Successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Fill All Details", Toast.LENGTH_SHORT).show()
            }

        }
        val pickImage =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                if (uri != null) {
                    binding.pickImage.setImageURI(uri)
                    foodImage = uri
                }
            }
        binding.pickImage.setOnClickListener {
            pickImage.launch("image/*")
        }
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun uploadData() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val menuRef = database.getReference("users/${userId}/menu")
        val menuKey = menuRef.push().key
        if (foodImage != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("images/${menuKey}.jpg")
            val uploadTask = imageRef.putFile(foodImage!!)

            uploadTask.addOnCompleteListener {
                imageRef.downloadUrl.addOnCompleteListener { downloadUri ->

                    val newItem = BaseModel(
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodDescription = foodDescription,
                        foodIngredients = foodIngredients,
                        foodImage = downloadUri.toString()
                    )
                    menuKey.let { key ->
                        menuRef.child(key!!).setValue(newItem)
                            .addOnCompleteListener {
                                Toast.makeText(
                                    this,
                                    "Data uploaded successfully",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    this,
                                    "Data upload failed",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                    }
                }
                    .addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Failed to get download URL",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        } else {
            Toast.makeText(
                this,
                "Add your Image",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}