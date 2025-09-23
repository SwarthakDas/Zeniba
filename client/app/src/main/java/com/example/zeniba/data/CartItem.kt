package com.example.zeniba.data

data class CartItem(

val id: Int,
val name: String,
val Description: String,
val price: Double,
val imageRes: Int,
var quantity: Int = 1

)
