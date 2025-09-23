package com.example.zeniba.data

data class Products(
    val _id: String,
    val brand: String,
    val category: String,
    val description: String,
    val images: List<Any>,
    val name: String,
    val price: Int,
    val seller: Seller,
    val stock: Int
)