package com.example.zeniba.data

data class ProductResponse(
    val data: List<Products>,
    val message: String,
    val statuscode: Int,
    val success: Boolean
)