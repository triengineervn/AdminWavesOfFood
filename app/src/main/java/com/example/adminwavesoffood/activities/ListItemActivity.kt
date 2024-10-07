package com.example.adminwavesoffood.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.adapters.ItemAdapter
import com.example.adminwavesoffood.databinding.ActivityListItemBinding
import com.example.adminwavesoffood.models.ItemModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ListItemActivity : AppCompatActivity() {
    private val binding: ActivityListItemBinding by lazy {
        ActivityListItemBinding.inflate(layoutInflater)
    }

    private lateinit var databaseReference: DatabaseReference
    private val itemList: MutableList<ItemModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().reference
        retrieveListItem()

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun retrieveListItem() {


        val itemsRef = databaseReference.child("menu")

        itemsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()

                for (foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(ItemModel::class.java)
                    menuItem?.let {
                        itemList.add(it)
                    }
                }


                // Setting up the RecyclerView with the adapter
                val adapter = ItemAdapter(this@ListItemActivity, itemList)
                binding.itemRecyclerView.layoutManager =
                    LinearLayoutManager(this@ListItemActivity)
                binding.itemRecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(
                    "ListItemActivity",
                    "Failed to read data: ${error.message}",
                    error.toException()
                )
            }
        })

    }
}
