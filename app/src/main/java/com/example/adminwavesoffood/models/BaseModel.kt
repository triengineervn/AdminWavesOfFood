package com.example.adminwavesoffood.models

import android.net.Uri

data class BaseModel(
    val foodName: String? = null,
    val foodPrice: String? = null,
    val foodImage: String? = null,
    val foodDescription: String? = null,
    val foodIngredients: String? = null,
)
