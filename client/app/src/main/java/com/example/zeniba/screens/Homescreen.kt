package com.example.zeniba.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.zeniba.R
import com.example.zeniba.data.AuthRepository
import com.example.zeniba.data.Products
import com.example.zeniba.data.RetrofitInstance
import com.example.zeniba.ui.theme.PoppinsFontFamily











@Composable
fun HomeScreen(navController: NavController)  {





    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color(0xFFF0F0EE),
        //topbar section

        topBar = {
            HomescreenTopBar()
        },


        // bottom bar of the section
        bottomBar = {

        },

        ) { innerPadding ->
        Box(
            modifier = Modifier.padding(
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + 10.dp,
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr) + 10.dp,
                top = innerPadding.calculateTopPadding() + 8.dp,
                bottom = innerPadding.calculateBottomPadding()
            ).fillMaxSize(),
        )
        {


            LazyColumn( userScrollEnabled = true,) {

                //Poster
                item { AutoSwipePoster()

                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp))
                }

                // Catagaries header
                item {
                    Row (modifier= Modifier
                        .fillMaxWidth(),

                        verticalAlignment = Alignment.CenterVertically){
                        Text(text = "Categories",
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 21.sp)
                        Spacer(modifier = Modifier

                            .width(124.dp))

                        Text(text = "See All",
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp)
                        Spacer(modifier = Modifier

                            .width(9.dp))

                        Box(modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(
                                color = Color(0xFFBEEE02)
                            ),
                            contentAlignment = Alignment.Center
                        ){
                            Icon(imageVector = Icons.Default.ArrowForward, contentDescription ="" )
                        }
                    }

                }

                //Catagorys
                item {
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(9.dp))

                    val categories = listOf("Science", "Math", )
                    Row(modifier = Modifier.height(200.dp)) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxHeight(),
                            userScrollEnabled = false, // Disable inner scrolling// constrain it
                        ) {
                            items(categories) { category ->
                                CatagoryCard(text = category)
                            }
                        }
                    }


                }

                //top products
                item{
                    Column {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(text = "Top Products",
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 21.sp,
                            color = Color(0xFF202020))
                        Spacer(modifier = Modifier.height(10.dp))



                        val TopProducts = listOf(R.drawable.img_2, R.drawable.img_2,R.drawable.img_2,R.drawable.img_2, R.drawable.img_2,R.drawable.img_2,R.drawable.img_2, R.drawable.img_2,R.drawable.img_2 )
                        LazyRow (userScrollEnabled = true,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)){
                            items(items=TopProducts){ Topproduct->
                                ProductImage(imageVector = Topproduct)

                            }
                        }


                    }
                }


            }




            //nav bar
            FloatingBottomBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp)
                    .padding(4.dp)
                    .fillMaxWidth()// margin from edges
                    .height(56.dp),
                selecteditem = 0,
                navController = navController
            )





        }
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



@Composable

fun HomescreenTopBar(modifier: Modifier= Modifier)
    {
        Row(
            modifier= modifier
                .statusBarsPadding()
                .fillMaxWidth()

                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween


        )
        {
            Row {

                Box(modifier=Modifier.size(37.dp)
                    .background(color=Color(0xFFD8CFFC),RoundedCornerShape(8.dp))
                    )
                {
                    Image(painterResource(R.drawable.avatar),"",
                        modifier=Modifier.align  (alignment = Alignment.Center)
                            .background(color=Color.Transparent))
                }
                Spacer(modifier = Modifier.width(4.dp))


                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text("Welcome Back",
                        fontSize = 10.sp,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Light)
                    Text("Jyoti",
                        fontSize = 15.sp,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Bold)
                }
            }

            var searchtext by remember {
                mutableStateOf("")
            }
            Spacer(modifier = Modifier.width(40.dp))
            SearchBar(text = searchtext, onTextChange ={} , modifier = Modifier.size(250.dp,50.dp))

        }
    }





@Preview(showBackground = true,showSystemUi = true)
@Composable
fun GreetingPreview() {

HomeScreen(navController = rememberNavController())

}