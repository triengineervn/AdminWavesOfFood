package com.example.adminwavesoffood.models

import android.net.Uri

data class BaseModel(
    val name: String? = null,
    val price: String? = null,
    val image: String? = null,
    val description: String? = null,
    val ingredients: String? = null,
)
