package com.example.zeniba.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zeniba.ui.theme.PoppinsFontFamily
import com.example.zeniba.ui.theme.ZenibaTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.time.format.TextStyle


@Composable
fun Splashscreen() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally


    ){


        Spacer(modifier = Modifier.weight(0.25f))

        Box(modifier = Modifier
            .shadow(5.dp, CircleShape,)
            .clip(CircleShape)
            .background(color = Color(0xFFF0F0EE))
            .size(134.dp, 134.dp),


            contentAlignment = Alignment.Center

        )
             {

            }
        Spacer(Modifier.height(30.dp))

        Text(
            text = "Zeniba",
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF202020),
// drop shadow effect
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 53.sp,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.5f),
                    offset = Offset(0f, 4f),
                    blurRadius = 6f
                )


            )
        )

        Text(
            text="Beautiful eCommerce UI \nKit for your online store",
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Light,
            color = Color(0xFF202020),
            fontSize = 19.sp
        )

        Spacer(Modifier.height(60.dp))

        GreenButton(text = "Let's get started", onClick ={} )

        Spacer(modifier = Modifier.weight(0.15f))




    }


}



@Composable
fun GreenButton(
    modifier: Modifier=Modifier,
    text:String,
    onClick: (String) -> Unit,
){
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth(0.89f)
            .height(61.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF9EFF00), // background color
            contentColor = Color(0xFF010101)
        ),
        shape = RoundedCornerShape(8.dp)// directly use CircleShape
    ) {
        Text(
            text = text,
            fontSize = 21.sp ,// use sp for text
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Light
        )
    }
}
@Preview(showBackground = true,showSystemUi = true)
@Composable
fun Preview() {

       Splashscreen()

}