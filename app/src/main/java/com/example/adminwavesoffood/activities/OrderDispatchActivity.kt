package com.example.adminwavesoffood.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.R
import com.example.adminwavesoffood.adapter.DeliveryAdapter
import com.example.adminwavesoffood.databinding.ActivityOrderDispatchBinding
import com.example.adminwavesoffood.models.DeliveryModel

class OrderDispatchActivity : AppCompatActivity() {
    private val binding: ActivityOrderDispatchBinding by lazy {
        ActivityOrderDispatchBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val items = mutableListOf(
            DeliveryModel("Ngan", this.getString(R.string.invalid_received)),
            DeliveryModel("Hang", this.getString(R.string.invalid_not_received)),
            DeliveryModel("Trong", this.getString(R.string.invalid_pending)),
            DeliveryModel("Tuan", this.getString(R.string.invalid_received)),
        )

        val adapter = DeliveryAdapter(items)
        binding.itemRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.itemRecyclerView.adapter = adapter

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}

