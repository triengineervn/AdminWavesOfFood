package com.example.adminwavesoffood.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.R
import com.example.adminwavesoffood.adapters.ItemAdapter
import com.example.adminwavesoffood.databinding.ActivityListItemBinding
import com.example.adminwavesoffood.models.ItemModel

class ListItemActivity : AppCompatActivity() {
    private val binding: ActivityListItemBinding by lazy {
        ActivityListItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        val items = mutableListOf(
            ItemModel("Pizza", "$11", R.drawable.menu_photo_1, 1),
            ItemModel("Burger", "$8", R.drawable.menu_photo_2, 1),
            ItemModel("Pasta", "$12", R.drawable.menu_photo_1, 1),
            ItemModel("Chicken", "$11", R.drawable.menu_photo_1, 1),
            ItemModel("Beef", "$18", R.drawable.menu_photo_1, 1),
            ItemModel("Fish", "$21", R.drawable.menu_photo_1, 1),
            ItemModel("Eggs", "$2", R.drawable.menu_photo_1, 1)
        )

        val adapter = ItemAdapter()
        binding.itemRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.itemRecyclerView.adapter = adapter
        adapter.setData(items)

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}