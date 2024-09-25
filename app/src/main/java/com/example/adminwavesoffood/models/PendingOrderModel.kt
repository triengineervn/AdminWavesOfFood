package com.example.adminwavesoffood.models

import androidx.core.content.ContextCompat.getString
import com.example.adminwavesoffood.R

data class PendingOrderModel(
    var name: String,
    var quantity: Int,
    var imageUriId: Int,
    var status: String = "Pending"
)
