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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.zeniba.R
import com.example.zeniba.ui.theme.PoppinsFontFamily

@Composable
fun CartScreen(navController: NavController) {

    var ItemCount=4
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color(0xFFF0F0EE),
        //topbar section

        topBar = {
            Row(modifier=Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(start = 15.dp),
                verticalAlignment = Alignment.CenterVertically
                ){
                Text("Cart",
                    fontSize = 28.sp,
                    fontFamily = PoppinsFontFamily)


                val CartItemCount =2
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color.White)


                ){
                    Text("$CartItemCount",
                        modifier = Modifier.align (Alignment.Center),
                        fontSize = 20.sp,
                        fontFamily = PoppinsFontFamily)


                }

            }
        },


        // bottom bar of the section
        bottomBar = {

        },

        ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(
                    innerPadding
                )
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),


                ) {
                Card(modifier = Modifier
                    .padding(15.dp)
                    .height(75.dp)
                    .fillMaxWidth()
                    , colors = CardDefaults.cardColors(Color.LightGray)

                ) {
                    Column (modifier = Modifier.padding(8.dp)){
                        Text(
                            text = "Shipping Address",
                            fontSize = 15.sp,
                            fontFamily = PoppinsFontFamily
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "26, Duong So 2, Thao Dien Ward, An Phu, \nDistrict 2, Ho Chi Minh city",
                                fontSize = 10.sp,
                                fontFamily = PoppinsFontFamily,


                            )


                            Icon(Icons.Default.Edit,"",
                                Modifier
                                    .background(color = Color(0xFFBEEE02), CircleShape)
                                    .padding(4.dp)
                                    )

                        }


                    }
                }







                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (ItemCount == 0) {
                        // Empty State
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 70.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(135.dp)
                                        .clip(CircleShape)
                                        .background(Color.White, CircleShape)
                                        .shadow(
                                            4.dp,
                                            shape = CircleShape,
                                            ambientColor = Color.Transparent
                                        )
                                ) {
                                    Icon(
                                        Icons.Default.ShoppingBag,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(50.dp),
                                        tint = Color(0xFFFF5790)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(70.dp))
                        }
                    } else {
                        // Show products instead
                        items(2) {
                            CartItemProduct(
                                img = R.drawable.img_4,
                                "hdkfj",
                                123,
                                3.4
                            )
                        }
                        item {

                        }
                    }

                    // Common section shown in both cases
                    item {
                        WhishlistSection()
                    }

                    // Example: More products
                    items(5) {
                        WishlistProductCardForCartSection(
                            img = R.drawable.img_4,
                            "hdkfj",
                            123,
                            3.4
                        )
                    }
                }










            }




            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                if (ItemCount != 0)
                {
                    PriceSection()

                }


                //nav bar
                FloatingBottomBar(
                    modifier = Modifier

                        .padding( start = 5.dp, end = 5.dp)
                        .padding(4.dp)
                        .fillMaxWidth()// margin from edges
                        .height(56.dp),
                    selecteditem = 4,
                    navController = navController
                )
            }






        }
    }
}


@Composable
fun CartItemProduct(
    img: Int,
    name: String,
    price: Int,
    rating: Double,
    description:String="Recycle Boucle Knit Cardigan Pinkhj jjbj ndkkhdued nxajnx"
){

    Card(
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
            .padding(15.dp),
        colors = CardDefaults.cardColors(Color.White)
    )
    {
        Row (
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)


        ){
            Box(modifier = Modifier
                .size(107.dp, 140.dp)
                .align(Alignment.CenterVertically)

            )
            {
                Image(painter = painterResource(img),
                    "",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                    ,
                    contentScale = ContentScale.Crop
                )
                Image(imageVector = Icons.Default.RemoveShoppingCart,"",
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(3.dp)
                        .background(color = Color(0xFFBEEE02), shape = CircleShape)
                        .padding(5.dp)
                        .clickable(true, "", onClick = {}),
                )

            }

            Column(
                modifier = Modifier.fillMaxHeight()
                    .padding(5.dp),
                verticalArrangement = Arrangement.SpaceBetween

            ) {

                Text(text = name.uppercase(),
                    fontFamily = PoppinsFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(text =description ,
                    fontFamily = PoppinsFontFamily,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "\$$price",
                    fontFamily = PoppinsFontFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFFF5790)
                )



                Row {
                    Text(text = "Size",
                        fontFamily = PoppinsFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(2.dp, color = Color.LightGray, shape = CircleShape)
                    ){
                        Text("S",
                            modifier = Modifier.align (Alignment.Center),
                            fontSize = 11.sp,
                            fontFamily = PoppinsFontFamily)


                    }

                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(2.dp, color = Color.LightGray, shape = CircleShape)
                    ){
                        Text("L",
                            modifier = Modifier.align (Alignment.Center),
                            fontSize = 11.sp,
                            fontFamily = PoppinsFontFamily)

                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(2.dp, color = Color.LightGray, shape = CircleShape)
                    ){
                        Text("M",
                            modifier = Modifier.align (Alignment.Center),
                            fontSize = 11.sp,
                            fontFamily = PoppinsFontFamily)

                    }
                    Spacer(modifier = Modifier.weight(1f))



                    Box(modifier = Modifier
                        .size(90.dp, 25.dp)
                        .background(color = Color.Black, shape = RoundedCornerShape(16.dp)))
                    {
                        var count  by remember  { mutableStateOf(1) }

                        Row (modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween){

                            Icon(Icons.Default.Remove,"",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(25.dp, 15.dp)
                                    .clickable(true, onClick = { count-- }))


                            Text("$count", color = Color.White,
                                fontSize = 16.sp,
                                fontFamily = PoppinsFontFamily,
                                fontWeight = FontWeight.Medium)
                            Icon(Icons.Default.Add,"",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(25.dp, 15.dp)
                                    .clickable(true, onClick = { count++ }))


                        }
                    }



                }


            }
        }
    }
}

@Composable
fun WishlistProductCardForCartSection(
    img: Int,
    name: String,
    price: Int,
    rating: Double,
    description:String="Recycle Boucle Knit Cardigan Pinkhj jjbj ndkkhdued nxajnx"
){
    Card(
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
            .padding(15.dp),
        colors = CardDefaults.cardColors(Color.White)
    )
    {
        Row (
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)


        ){
            Box(modifier = Modifier
                .size(107.dp, 140.dp)
                .align(Alignment.CenterVertically)

            )
            {
                Image(painter = painterResource(img),
                    "",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                    ,
                    contentScale = ContentScale.Crop
                )
                Image(imageVector = Icons.Default.Favorite,"",
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(3.dp)
                        .background(color = Color(0xFFBEEE02), shape = CircleShape)
                        .padding(5.dp)
                        .clickable(true, "", onClick = {}),
                )

            }

            Column(
                modifier = Modifier.fillMaxHeight()
                    .padding(6.dp),
                verticalArrangement = Arrangement.SpaceBetween

            ) {

                Text(text = name.uppercase(),
                    fontFamily = PoppinsFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(text =description ,
                    fontFamily = PoppinsFontFamily,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "\$$price",
                    fontFamily = PoppinsFontFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFFF5790)
                )
                Text(text = "$rating Ratings",
                    fontFamily = PoppinsFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                )


                Row {
                    Text(text = "Size",
                        fontFamily = PoppinsFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(2.dp, color = Color.LightGray, shape = CircleShape)
                    ){
                        Text("S",
                            modifier = Modifier.align (Alignment.Center),
                            fontSize = 11.sp,
                            fontFamily = PoppinsFontFamily)


                    }

                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(2.dp, color = Color.LightGray, shape = CircleShape)
                    ){
                        Text("L",
                            modifier = Modifier.align (Alignment.Center),
                            fontSize = 11.sp,
                            fontFamily = PoppinsFontFamily)

                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(2.dp, color = Color.LightGray, shape = CircleShape)
                    ){
                        Text("M",
                            modifier = Modifier.align (Alignment.Center),
                            fontSize = 11.sp,
                            fontFamily = PoppinsFontFamily)

                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(2.dp, color = Color.LightGray, shape = CircleShape)
                    ){
                        Text("XL",
                            modifier = Modifier.align (Alignment.Center),
                            fontSize = 11.sp,
                            fontFamily = PoppinsFontFamily)

                    }
                    Spacer(modifier = Modifier.weight(1f))


                    Icon(imageVector = Icons.Default.AddShoppingCart,
                        "",


                        )




                }


            }
        }
    }

}


@Composable
fun WhishlistSection(){

    Column (modifier = Modifier.fillMaxSize()){

        Text("Most Popular",
            modifier=Modifier.padding(15.dp),
            fontSize = 22.sp,
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Bold)
    }


}


@Composable
fun PriceSection(totalPrice:Int=110){


    Column {

        Row (
            modifier = Modifier.fillMaxWidth().height(20.dp)
                .background(Color(0xFFBEEE02))
        ){

        }

        Row (
            modifier = Modifier.fillMaxWidth().height(65.dp)
                .shadow(8.dp)
                .background(Color.White)
                .padding(start = 20.dp, end = 20.dp)
                ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,

            ){
            Text(
                "Total $${totalPrice}",
                fontFamily = PoppinsFontFamily,
                fontSize = 22.sp,

                )
            GreenButton(text = "Checkout", modifier = Modifier.size(147.dp,45.dp), onClick = { })

        }
    }


}

@Preview(showBackground = true,showSystemUi = true)
@Composable
fun PreviewCart() {
//    var searchtext by remember {
//        mutableStateOf("")
//    }
//   SearchBar(text=searchtext,modifier = Modifier.height(30.dp), onTextChange = {searchtext=it},)


}