package com.example.adminwavesoffood.models

import com.example.adminwavesoffood.R

data class ItemModel(
    var name: String? = null,
    var price: String? = null,
    var image: String? = "",
    var quantity: Int = 1
)
