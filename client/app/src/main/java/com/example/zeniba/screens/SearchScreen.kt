package com.example.zeniba.screens
import androidx.compose.animation.AnimatedVisibility
import com.example.zeniba.R
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.zeniba.data.AuthRepository
import com.example.zeniba.data.Products
import com.example.zeniba.ui.theme.PoppinsFontFamily







@Composable
fun SearchScreen(navController: NavController){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repo = remember { AuthRepository(context) }

    var products by remember { mutableStateOf<List<Products>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMsg by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        try {
            products=repo.fetchProducts()
        }catch (e: Exception) {
            errorMsg = e.message
        } finally {
            isLoading = false
        }

    }
    if(isLoading)
    {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (errorMsg != null) {
        Text("Error: $errorMsg", color = Color.Red)
    } else {
        Search(navController,products)

    }


}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Search(navController: NavController, products: List<Products>){
    val listState = rememberLazyGridState()



        Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color(0xFFF0F0EE),
        //topbar section


        topBar = {
            Column {
                SearchTopbar()
                // Filter bar that collapses

                    FilterBar(liststate = listState)

            }

        },


        )
        { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {



                // Main content
                LazyVerticalGrid(
                    state = listState,
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    userScrollEnabled = true, 

                ) {
                    items(products) { item ->
                        SearchProductCard(image =R.drawable.img_2,item.description,item.price)
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
                navController=navController
            )





        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBar(
    modifier: Modifier = Modifier,
    liststate: LazyGridState?=null
) {
    val filterOptions = listOf(
        "Trending Dress" ,
        "Top rated" ,
        "RAM" ,
        "Internal Storage" ,
        "Brand" ,
        "Price"
    )
    val visible: Boolean
    if(liststate!=null)
    {
      visible=  rememberScrollDirectionState(liststate)
    }else{
        visible=true
    }

    AnimatedVisibility(
        visible=visible,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
    ){
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F0EE))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            items(filterOptions.size) { index ->
                val label= filterOptions[index]

                FilterChip(
                    onClick = { /* Handle filter click */ },
                    label = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = label,
                                fontSize = 14.sp,
                                fontFamily = PoppinsFontFamily,
                                color = Color.White
                            )

                        }
                    },
                    selected = false,
                    modifier = Modifier.height(36.dp),
                    border = null,
                    shape = RoundedCornerShape(18.dp),
                    colors = FilterChipDefaults.filterChipColors(containerColor = Color.Black)


                )
            }
        }
    }

}

@Composable
fun SearchTopbar(
    searchText:String="Dress for men",
    modifier: Modifier=Modifier,
){
    Row (
        modifier=modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.White)
            .statusBarsPadding()
            .padding(start = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically,


    ){
        Icon(imageVector = Icons.Default.ArrowBackIosNew,"",
            modifier= Modifier.size(19.dp))

        Spacer(modifier = Modifier.width(10.dp))
        Text(searchText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = PoppinsFontFamily)


         Spacer(modifier = Modifier.weight(.9f))


        Icon(imageVector = Icons.Default.Search,"",
            modifier= Modifier.size(19.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Icon(imageVector = Icons.Default.FavoriteBorder,"",
            modifier= Modifier.size(19.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Icon(imageVector = Icons.Default.ShoppingBag,"",
            modifier= Modifier.size(19.dp))
    }

}


@Composable

fun SearchProductCard(image: Int,
                      prductDetails: String="Lorem ipsum dolor sit amet consectetur"
                      ,price: Int=200)
{

    Card(modifier= Modifier
        .padding(8.dp)
        .size(163.dp, 258.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),




    ) {
        Column (modifier = Modifier
            .fillMaxSize(1f)
            .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ){

            Box(
                modifier = Modifier
                    .height(172.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))

            ){
                Image(painter = painterResource(image),
                    "",
                    modifier = Modifier

                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                        ,
                    contentScale = ContentScale.Crop
                )

            }


            Text(prductDetails,
                fontSize = 12.sp,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Normal,
                maxLines = 2
                )
            Text("\$${price}",
                    fontSize = 17.sp,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5790))

        }
    }
}



@Composable
fun rememberScrollDirectionState(
    listState: LazyGridState
): Boolean {
    var previousScrollOffset by remember { mutableStateOf(0) }
    var isScrollingUp by remember { mutableStateOf(false) }

    // Track scroll changes
    LaunchedEffect(listState) {
        snapshotFlow {
            listState.firstVisibleItemIndex * 1000 + listState.firstVisibleItemScrollOffset
        }.collect { currentOffset ->
            val isCurrentlyScrollingUp = currentOffset > previousScrollOffset

            if (kotlin.math.abs(currentOffset - previousScrollOffset) > 5) {
                isScrollingUp = isCurrentlyScrollingUp
                previousScrollOffset = currentOffset
            }
        }
    }

    // Return derived state: should show filter bar or not
    val showFilterBar by remember {
        derivedStateOf {
            if (listState.firstVisibleItemIndex == 0 &&
                listState.firstVisibleItemScrollOffset < 10
            ) {
                true // Always show at top
            } else {
                !isScrollingUp // Show when scrolling down
            }
        }
    }

    return showFilterBar
}

@Preview(showBackground = true,showSystemUi = true)
@Composable
fun presearch(){
//    var products by remember { mutableStateOf<List<Products>>(emptyList()) }
  val navController = rememberNavController()


    SearchScreen(navController)


}