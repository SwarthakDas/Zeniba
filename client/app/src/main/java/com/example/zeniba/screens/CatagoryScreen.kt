package com.example.zeniba.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zeniba.R
import com.example.zeniba.ui.theme.PoppinsFontFamily
import kotlinx.coroutines.delay


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatagoryScreen(NavController:NavController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color(0xFFF0F0EE),
        //topbar section

        topBar = {
            CatagaryTopBar()
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
        ) {


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
                selecteditem = 1,
                navController = NavController
            )





        }
    }
}




@Composable
fun ProductImage(modifier: Modifier = Modifier, imageVector: Int){
    Box(
        modifier=modifier.size(60.dp,60.dp)
            .shadow(
                elevation = 6.dp,       // how strong the shadow is
                shape = CircleShape,    // match background shape
                clip = false  ,
                // keep shadow outside the circle
            )
            .clip(shape =RoundedCornerShape(50.dp))
            .background(Color(color = 0xFFF0F0EE))
            .padding(8.dp)

    ){
        Image(painterResource(imageVector), "nd",
            modifier= Modifier.clip(CircleShape)
                .fillMaxSize())
    }



}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoSwipePoster() {
    val posters = listOf(
        R.drawable.img,
        R.drawable.img,
        R.drawable.img
    )

    val pagerState = rememberPagerState(pageCount = { posters.size })

    // Auto scroll effect
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000) // 3 seconds delay
            val nextPage = (pagerState.currentPage + 1) % posters.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Pager
        HorizontalPager(state = pagerState) { page ->
            Card(
                modifier = Modifier

                    .fillMaxWidth()
                    .height(180.dp),

            ) {
                Image(
                    painter = painterResource(id = posters[page]),
                    contentDescription = "Poster",
                    contentScale = ContentScale.Crop,
                            modifier= Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                )
            }
        }

        // Indicator (dots)
        PagerIndicator(totalPages = posters.size, currentPage = pagerState.currentPage)
    }
}


@Composable
fun PagerIndicator(
    totalPages: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(totalPages) { index ->
            val isSelected = index == currentPage
            val indicatorModifier = if (isSelected) {
                Modifier
                    .padding(4.dp)
                    .width(30.dp)   // bar style
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF9EFF00))
            } else {
                Modifier
                    .padding(4.dp)
                    .size(8.dp)    // circle style
                    .clip(CircleShape)
                    .background(Color.LightGray)
            }
            Box(
                modifier=indicatorModifier

            )
        }
    }
}


@Composable
fun CatagoryCard(text: String){
    Card(onClick = { /*TODO*/},
        colors= CardColors(Color.White,Color.Black, Color.White, Color.Black),
        modifier = Modifier.size(165.dp,192.dp)

        ,

    ) {

        Column(
            modifier = Modifier
                .padding(7.5.dp)

        ) {

            Row {
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                ){
                    Image(painter = painterResource(id = R.drawable.img_2),
                        contentDescription = "",
                        modifier=Modifier.size(73.05.dp,73.05.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                ){
                    Image(painter = painterResource(id = R.drawable.img_2),
                        contentDescription = "",
                        modifier=Modifier.size(73.05.dp,73.05.dp),
                        contentScale = ContentScale.Crop
                    )
                }

            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                ){
                    Image(painter = painterResource(id = R.drawable.img_2),
                        contentDescription = "",
                        modifier=Modifier.size(73.05.dp,73.05.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                ){
                    Image(painter = painterResource(id = R.drawable.img_2),
                        contentDescription = "",
                        modifier=Modifier.size(73.05.dp,73.05.dp),
                        contentScale = ContentScale.Crop
                    )
                }

            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = PoppinsFontFamily
                )
        }
    }

}


@Composable
fun Banner(modifier: Modifier=Modifier)
{
    Card(onClick = { /*TODO*/ }, modifier = modifier) {
        Image(painter = painterResource(id = R.drawable.img)
            , contentDescription ="",
             modifier= Modifier
                 .fillMaxWidth()
                 .height(180.dp)
        )
    }
}

@Composable
fun CatagaryTopBar( modifier: Modifier = Modifier)
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
        Text(text = "Zeniba",
            fontSize = 28.sp,
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Bold
        )

        var searchtext by remember {
            mutableStateOf("")
        }
        Spacer(modifier = Modifier.width(40.dp))
        SearchBar(text = searchtext, onTextChange ={} , modifier = Modifier.size(250.dp,50.dp))

    }
}


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    text: String,
    ) {


    TextField(
        value = text,
        onValueChange = onTextChange,
        placeholder = { Text("Search",
            fontSize = 16.sp,
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Medium) },
        shape = RoundedCornerShape(40.dp),
        singleLine = true,

        modifier = modifier.fillMaxWidth(0.90f),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Gray,
            unfocusedContainerColor = Color(0xFFFFFFFF),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            Icon(imageVector = Icons.Default.CameraAlt, contentDescription = "")
        }



    )
}

@Composable
fun FloatingBottomBar(modifier: Modifier = Modifier, selecteditem: Int = 0, navController: NavController) {


    var selectedIndex by remember { mutableStateOf(selecteditem) }

    val items = listOf(
        Icons.Default.Home to "home",
        Icons.Default.Search to "search",
        Icons.Default.Person to "profile",
        Icons.Default.Favorite to "whishlist",
        Icons.Default.ShoppingBag to "cart"
    )


    Card(
            modifier = modifier.fillMaxWidth(0.9f),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF202020)),


            ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),

                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,

            ) {
                items.forEachIndexed { index, (icon, route) ->
                    val isSelected = index == selectedIndex

                    Surface(
                        shape = CircleShape,
                        color = if (isSelected) Color(0xFFBEEE02) else Color.Transparent,
                        shadowElevation = if (isSelected) 4.dp else 0.dp,
                        modifier = Modifier
                            .size(50.dp,40.dp)
                            .clip(CircleShape)
                            .clickable {
                                selectedIndex = index
                                navController.navigate(route) {
                                }
                            }
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (isSelected) Color.Black else Color(0xFFBEEE02),
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                }



            }
        }


}



@Preview(showBackground = true,showSystemUi = true)
@Composable
fun PreviewCtaragory() {
//    var searchtext by remember {
//        mutableStateOf("")
//    }
//   SearchBar(text=searchtext,modifier = Modifier.height(30.dp), onTextChange = {searchtext=it},)


}
