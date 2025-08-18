package com.example.zeniba.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zeniba.R
import com.example.zeniba.ui.theme.PoppinsFontFamily
import com.example.zeniba.ui.theme.ZenibaTheme

@Composable
fun HomeScreen() {

    Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(10.dp)

            ){

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Column {
                Text("Welcome Back", fontSize = 14.sp, color = Color.Gray)
                Text("Jobby", fontFamily = PoppinsFontFamily, fontSize = 20.sp)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(

            modifier=Modifier.fillMaxSize(1f)
        ) {
            // Top Profile + Search


            // Banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black)
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Text(
                            "Get attractive discounts of the year here",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(Color(0xFFB0FF57))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("10h 5m 3d", color = Color.Black)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Categories
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val categories = listOf("Popular", "Jacket", "Shoes", "Pants","Jacket", "Shoes", "Pants")
                    items(categories.size) { index ->
                        CategoryChip(text = categories[index])
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Popular Products Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Popular Product", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("See All", color = Color.Blue)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Popular Products List
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    val products = listOf(
                        ProductData("Wake - Hoodie", "$129", R.drawable.ic_launcher_background),
                        ProductData("Wake - Hoodie", "$129", R.drawable.ic_launcher_background)
                    )
                    items(products.size) { index ->
                        ProductCard(products[index])
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    val products = listOf(
                        ProductData("Wake - Hoodie", "$129", R.drawable.ic_launcher_background),
                        ProductData("Wake - Hoodie", "$129", R.drawable.ic_launcher_background)
                    )
                    items(products.size) { index ->
                        ProductCard(products[index])
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    val products = listOf(
                        ProductData("Wake - Hoodie", "$129", R.drawable.ic_launcher_background),
                        ProductData("Wake - Hoodie", "$129", R.drawable.ic_launcher_background)
                    )
                    items(products.size) { index ->
                        ProductCard(products[index])
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Bottom Navigation (Static)

        }
        BottomNavigationBar()
    }


}

@Composable
fun CategoryChip(text: String) {
    Box(
        modifier = Modifier
            .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(50))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text)
    }
}

data class ProductData(val name: String, val price: String, val imageRes: Int)

@Composable
fun ProductCard(product: ProductData) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = product.imageRes),
            contentDescription = product.name,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(product.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(product.price, color = Color.Black)
    }
}

@Composable
fun BottomNavigationBar() {


    NavigationBar {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
                .background(Color.Black)
                .padding(vertical = 12.dp, horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.Transparent)
                )
            }
        }
    }

}

@Preview(showBackground = true,showSystemUi = true)
@Composable
fun GreetingPreview() {
    ZenibaTheme {
        HomeScreen()
    }
}