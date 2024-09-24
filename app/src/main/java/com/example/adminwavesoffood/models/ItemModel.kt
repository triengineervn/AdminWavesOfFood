package com.example.adminwavesoffood.models

data class ItemModel(
    var name: String,
    var price: String,
    var imageResId: Int,
    var quantity: Int = 1
)
