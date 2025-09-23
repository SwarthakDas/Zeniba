package com.example.zeniba.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.navigation.compose.rememberNavController
import com.example.zeniba.R
import com.example.zeniba.ui.theme.PoppinsFontFamily


@Composable
fun PoductScreen(){
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color(0xFFF0F0EE),
        //topbar section


        topBar = {
            Column {
                ProductTopbar()
                // Filter bar that collapses



            }

        },


        )  { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
            ){
                item{
                   ProductCard(modifier = Modifier.fillMaxWidth().padding(15.dp))

                }
                item {
                    ProductDetailsColumn(modifier = Modifier.padding(15.dp))
                }
                item { 
                    MostPopularSection(modifier = Modifier.padding(15.dp).fillMaxWidth().padding(0.dp))
                }
            }







        }
    }
}

@Composable
fun ProductCard(
    price: String = "$129",
    modifier: Modifier = Modifier,
    onFavoriteClick: () -> Unit = {},
    onCardClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(320.dp)
            .height(400.dp)
            .clickable { onCardClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Product Image img_2
            Image(
                painter = painterResource(id =R.drawable.img_5),
                contentDescription = "Product Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Heart/Favorite Icon - Top Right
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(40.dp)
                    .clickable { onFavoriteClick() },
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Add to favorites",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Price Badge - Bottom Left
            Surface(
                modifier = Modifier

                    .align(Alignment.BottomStart)
                    .padding(10.dp)
                    .size(100.dp,50.dp)
                    ,
                shape = RoundedCornerShape(16.dp),
                color = Color.White,

            ) {
                Text(
                    text = price,
                    color = Color.Black,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                        .align (Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun ProductTopbar(

    modifier: Modifier=Modifier,
){
    Row (
        modifier=modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.White)
            .statusBarsPadding()
            .padding(start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween


        ){
        Box(
            modifier=Modifier.background(color = Color(0xFFEEECEC), CircleShape)
        ){
            Icon(imageVector = Icons.Default.ArrowBackIosNew,"",
                modifier= Modifier.padding(4.dp)
                    .size(19.dp)
                    .align  (Alignment.Center)
            )
        }


        Text("Product Details",
            fontSize = 21.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = PoppinsFontFamily)





        Box(
            modifier=Modifier.background(color =Color(0xFFEEECEC), CircleShape)
        ){
            Icon(imageVector = Icons.Default.ShoppingBag,"",
                modifier= Modifier.padding(4.dp)
                    .size(19.dp)
                    .align  (Alignment.Center)
                    )
        }

    }

}


@Composable
fun ProductDetailsColumn(
    price: String = "$17.00",
    description: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed diam nonumy eirmod scelerisque eu mauris id, pretium pulvinar",
    modifier: Modifier = Modifier,
    productName: String = "Blue Cotton Hoodie",
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(2.dp)

    ) {
        Text(
            text = productName,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFontFamily
            ),
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        // Price Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = price,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = PoppinsFontFamily
            )

            Surface(
                modifier = Modifier.size(24.dp),
                shape = CircleShape,
                color = Color(0xFF4CAF50)
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Available",
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = description,
            fontSize = 14.sp,
            color = Color.DarkGray,
            lineHeight = 20.sp,
            fontFamily = PoppinsFontFamily

        )

        Spacer(modifier = Modifier.height(24.dp))

        // Variations Section
        VariationsSection()

        Spacer(modifier = Modifier.height(24.dp))

        // Specifications Section
        SpecificationsSection()

        Spacer(modifier = Modifier.height(24.dp))

        // Size Guide Section
        SizeGuideSection()

        Spacer(modifier = Modifier.height(24.dp))

        // Delivery Section
        DeliverySection()

        Spacer(modifier = Modifier.height(24.dp))

        // Rating & Reviews Section
        RatingReviewsSection()
    }
}

@Composable
fun VariationsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Variations",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            fontFamily = PoppinsFontFamily

        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Pink",
                fontSize = 12.sp,
                color = Color.Gray,
                fontFamily = PoppinsFontFamily

            )
            Text(
                text = "M",
                fontSize = 12.sp,
                color = Color.Gray,
                fontFamily = PoppinsFontFamily

            )
            Surface(
                modifier = Modifier.size(20.dp),
                shape = CircleShape,
                color = Color(0xFF4CAF50)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "View variations",
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Product variation image
    Surface(
        modifier = Modifier.size(40.dp),
        shape = RoundedCornerShape(8.dp),
        color = Color.Gray.copy(alpha = 0.1f)
    ) {
        // Placeholder for product variation image
        Icon(
            imageVector = Icons.Filled.Checkroom,
            contentDescription = "Product variation",
            tint = Color.Gray,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun SpecificationsSection() {
    Column {
        Text(
            text = "Specifications",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Material
        Text(
            text = "Material",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            fontFamily = PoppinsFontFamily

        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Cotton 95%",
                fontSize = 12.sp,
                color = Color.Gray,
                fontFamily = PoppinsFontFamily

            )
            Text(
                text = "Nylon 5%",
                fontSize = 12.sp,
                color = Color.Gray,
                fontFamily = PoppinsFontFamily

            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Origin
        Text(
            text = "Origin",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            fontFamily = PoppinsFontFamily

        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "EU",
            fontSize = 12.sp,
            color = Color.Gray,
            fontFamily = PoppinsFontFamily

        )
    }
}

@Composable
fun SizeGuideSection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Size guide",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontFamily = PoppinsFontFamily

            )

            Surface(
                modifier = Modifier.size(20.dp),
                shape = CircleShape,
                color = Color(0xFF4CAF50)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "View size guide",
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Text(text = "Size",
                fontFamily = PoppinsFontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
            )

            Spacer(modifier = Modifier.width(20.dp))

            Box(
                modifier = Modifier.size(24.dp)
                    .clip(CircleShape)
                    .border(2.dp, color = Color.LightGray, shape = CircleShape)
            ){
                Text("S",
                    modifier = Modifier.align (Alignment.Center),
                    fontSize = 14.sp,
                    fontFamily = PoppinsFontFamily)


            }

            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier.size(24.dp)
                    .clip(CircleShape)
                    .border(2.dp, color = Color.LightGray, shape = CircleShape)
            ){
                Text("L",
                    modifier = Modifier.align (Alignment.Center),
                    fontSize = 14.sp,
                    fontFamily = PoppinsFontFamily)

            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier.size(24.dp)
                    .clip(CircleShape)
                    .border(2.dp, color = Color.LightGray, shape = CircleShape)
            ){
                Text("M",
                    modifier = Modifier.align (Alignment.Center),
                    fontSize = 14.sp,
                    fontFamily = PoppinsFontFamily)

            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier.size(24.dp)
                    .clip(CircleShape)
                    .border(2.dp, color = Color.LightGray, shape = CircleShape)
            ){
                Text("XL",
                    modifier = Modifier.align (Alignment.Center),
                    fontSize = 14.sp,
                    fontFamily = PoppinsFontFamily)

            }








        }
    }

}

@Composable
fun DeliverySection() {
    Column {
        Text(
            text = "Delivery",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            fontFamily = PoppinsFontFamily

        )

        Spacer(modifier = Modifier.height(12.dp))

        // Standard delivery
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Standard",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontFamily = PoppinsFontFamily

                )
                Text(
                    text = "5-7 days",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = PoppinsFontFamily

                )
            }
            Text(
                text = "$3.00",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                fontFamily = PoppinsFontFamily

            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Express delivery
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Express",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontFamily = PoppinsFontFamily

                )
                Text(
                    text = "1-2 days",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = PoppinsFontFamily

                )
            }
            Text(
                text = "$12.00",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                fontFamily = PoppinsFontFamily

            )
        }
    }
}

@Composable
fun RatingReviewsSection() {
    Column {
        Text(
            text = "Rating & Reviews",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            fontFamily = PoppinsFontFamily

        )

        Spacer(modifier = Modifier.height(12.dp))

        // Star rating
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(4) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Star",
                    tint = Color.Red,
                    modifier = Modifier.size(16.dp)
                )
            }
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = "Empty star",
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "4/5",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 4.dp),
                fontFamily = PoppinsFontFamily

            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Review item
        ReviewItem()

        Spacer(modifier = Modifier.height(16.dp))

        // View all reviews button
        Button(
            onClick = { /* Handle view all reviews */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "View All Reviews",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = PoppinsFontFamily

            )
        }
    }
}

@Composable
fun ReviewItem() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Profile picture placeholder
        Surface(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            color = Color.Gray.copy(alpha = 0.3f)
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Profile picture",
                tint = Color.Gray,
                modifier = Modifier.padding(8.dp)
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Veronika",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                fontFamily = PoppinsFontFamily

            )

            Spacer(modifier = Modifier.height(4.dp))

            // User rating
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                repeat(4) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Star",
                        tint = Color.Red,
                        modifier = Modifier.size(12.dp)
                    )
                }
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = "Empty star",
                    tint = Color.Gray,
                    modifier = Modifier.size(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat.",
                fontSize = 12.sp,
                color = Color.Gray,
                lineHeight = 16.sp,
                fontFamily = PoppinsFontFamily

            )
        }
    }
}



@Preview(showBackground = true,showSystemUi = true)
@Composable
fun Productsearch(){
//    var products by remember { mutableStateOf<List<Products>>(emptyList()) }
    PoductScreen()


}