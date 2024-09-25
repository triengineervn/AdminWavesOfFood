package com.example.adminwavesoffood.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.R
import com.example.adminwavesoffood.adapter.PendingOrdersAdapter
import com.example.adminwavesoffood.databinding.ActivityPendingOrdersBinding
import com.example.adminwavesoffood.models.PendingOrderModel

class PendingOrdersActivity : AppCompatActivity() {

    private val binding: ActivityPendingOrdersBinding by lazy {
        ActivityPendingOrdersBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pendingOrdersItem = mutableListOf(
            PendingOrderModel("John Doe", 2, R.drawable.menu_photo_1),
            PendingOrderModel("John Doe", 5, R.drawable.menu_photo_1),
            PendingOrderModel("John Doe", 8, R.drawable.menu_photo_1),
            PendingOrderModel("John Doe", 12, R.drawable.menu_photo_1)
        )
        val adapter = PendingOrdersAdapter(pendingOrdersItem)
        binding.pendingOrdersRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.pendingOrdersRecyclerView.adapter = adapter

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}