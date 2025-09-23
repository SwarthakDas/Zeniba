package com.example.zeniba.screens

import android.R.attr.maxWidth
import android.R.attr.x
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun WishlistScreen(navController: NavController) {

    var ItemCount=2
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color(0xFFF0F0EE),
        //topbar section

        topBar = {
            WishlistTopbar()
        },


        // bottom bar of the section
        bottomBar = {

        },

        ) { innerPadding ->
        Box(
            modifier = Modifier.padding(
               innerPadding
            ).fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),

            )
            {


                if(ItemCount==0)
                {

                    Spacer(modifier=Modifier.height(70.dp))
                    Box(
                        modifier = Modifier.size(135.dp)

                            .clip(CircleShape)
                            .background(Color.White,CircleShape)
                            .shadow(4.dp, shape = CircleShape, ambientColor = Color.Transparent)
                            .align  (Alignment.CenterHorizontally),

                    ){
                        Icon(Icons.Default.FavoriteBorder,"",
                            modifier = Modifier.align  (Alignment.Center)
                                .size(50.dp),
                            tint = Color(0xFFFF5790)
                                )
                    }

                    Spacer(modifier=Modifier.height(70.dp))

                    MostPopularSection(modifier = Modifier.padding(15.dp).fillMaxWidth())


                }else {
                    FilterBar()

                    LazyColumn (

                    ){
                        items(ItemCount){
                            WishlistProductCard(img =R.drawable.img_4,"hdkfj",123,3.4)

                        }
                        item {
                            MostPopularSection()

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
                selecteditem = 3,
                navController=navController
            )





        }
    }
}




@Composable
fun WishlistTopbar(){
    Column (
        modifier = Modifier.padding(15.dp)
    )
    {
        Text("Wishlist",
            modifier=Modifier
                .fillMaxWidth()
                .height(70.dp)
                .statusBarsPadding()
                ,
            fontSize = 28.sp,
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(14.dp))

        Row ( modifier=Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Text(text = "Recently viewed",
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 21.sp)






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

        Spacer(modifier = Modifier.height(14.dp))

        val TopProducts = listOf(R.drawable.img_2, R.drawable.img_2,R.drawable.img_2,R.drawable.img_2, R.drawable.img_2,R.drawable.img_2,R.drawable.img_2, R.drawable.img_2,R.drawable.img_2 )
        LazyRow (userScrollEnabled = true,
            horizontalArrangement = Arrangement.spacedBy(12.dp)){
            items(items=TopProducts){ Topproduct->
                ProductImage(imageVector = Topproduct)

            }
        }

    }
}



@Composable
fun WishlistProductCard(
    img: Int,
    name: String,
    price: Int,
    rating: Double,
    description:String="Recycle Boucle Knit Cardigan Pinkhj jjbj ndkkhdued nxajnx"
){
    Card(
        modifier = Modifier
            .height(160.dp)
            .fillMaxWidth()
            .padding(15.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row (
            modifier = Modifier.fillMaxSize()
                .padding(10.dp)


        ){
            Box(modifier = Modifier
                .size(107.dp,140.dp)
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
                Image(imageVector = Icons.Default.RestoreFromTrash,"",
                    modifier = Modifier.align ( Alignment.BottomStart )
                        .padding(4.dp)
                        .background(color = Color(0xFFBEEE02), shape = CircleShape)
                        .padding(4.dp)
                        .clickable(true,"", onClick = {}),
                    )

            }

            Column(

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
                        modifier = Modifier.size(24.dp)
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
                        modifier = Modifier.size(24.dp)
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
                        modifier = Modifier.size(24.dp)
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
                        modifier = Modifier.size(24.dp)
                            .clip(CircleShape)
                            .border(2.dp, color = Color.LightGray, shape = CircleShape)
                    ){
                        Text("XL",
                            modifier = Modifier.align (Alignment.Center),
                            fontSize = 11.sp,
                            fontFamily = PoppinsFontFamily)

                    }
                    Spacer(modifier = Modifier.weight(1f))


                    Icon(imageVector = Icons.Default.Favorite,
                        "",
                        tint = Color.Red,

                        )




                }


            }
        }
    }

}

@Composable
fun MostPopularSection(modifier: Modifier=Modifier){
    Column (


    )
    {


        Row ( modifier=modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Text("Most Popular",
                modifier=Modifier,
                fontSize = 22.sp,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Bold)






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

        Spacer(modifier = Modifier.height(14.dp))

        val TopProducts = listOf(R.drawable.img_2, R.drawable.img_2,R.drawable.img_2,R.drawable.img_2, R.drawable.img_2,R.drawable.img_2,R.drawable.img_2, R.drawable.img_2,R.drawable.img_2 )
        LazyRow (userScrollEnabled = true,
            horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier
            ){
            items(items=TopProducts){ Topproduct->
                Card(
                    modifier = Modifier.size(104.dp,140.dp),
                    colors= CardDefaults.cardColors(Color.White)
                ) {
                    Column(


                    ) {
                        Image(painter = painterResource(R.drawable.img_5),
                            "",
                            modifier = Modifier.fillMaxWidth()
                                .height(104.dp)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )


                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween
                        ){

                            Row {
                                Text("108")

                                Icon(imageVector = Icons.Default.Favorite,
                                    "",
                                    tint = Color.Red,
                                    modifier = Modifier.size(11.dp)
                                )

                            }

                            Text("New")
                        }
                    }


                }

            }
        }

    }

}

@Preview(showBackground = true,showSystemUi = true)
@Composable
fun PreviewWishList() {
//    var searchtext by remember {
//        mutableStateOf("")
//    }
//   SearchBar(text=searchtext,modifier = Modifier.height(30.dp), onTextChange = {searchtext=it},)


}
