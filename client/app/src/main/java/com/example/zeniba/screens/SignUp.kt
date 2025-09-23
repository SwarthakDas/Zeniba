package com.example.zeniba.screens

import androidx.compose.foundation.Image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zeniba.R
import com.example.zeniba.ui.theme.PoppinsFontFamily


@Composable
fun SignUp(navController: NavHostController) {




        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFF0F0EE))
                .padding(10.dp, 30.dp, 10.dp, 10.dp),



        ){

            Spacer(modifier = Modifier.width(13.dp)
                )

            Text(text ="Create \n" +
                    "Account",
                fontSize = 50.sp ,// use sp for text
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Bold,
                letterSpacing = -0.5.sp,
                lineHeight = 54.sp,

            )
            Spacer(modifier = Modifier.height(36.dp))


            Image(painter = painterResource(id = R.drawable.upload_photo),
                contentDescription = "",
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
                    .padding(10.dp, 0.dp),
                contentScale = ContentScale.Fit,


            )

            Spacer(modifier = Modifier.height(36.dp))

            Column(modifier= Modifier

                .fillMaxWidth(),
                horizontalAlignment= Alignment.CenterHorizontally

            ) {


                Spacer(modifier = Modifier.height(16.dp))

                var username by remember { mutableStateOf("") }
                TextInput(
                    text = username,
                    placeholder = "Username",
                    onTextChange = { username = it },
                    leadingIcon = Icons.Default.Person,
                    leadingIconDescription = "Username"
                )

                Spacer(modifier = Modifier.height(16.dp))



                var email by remember { mutableStateOf("") }
                TextInput(
                    text = email,
                    placeholder = "Email",
                    onTextChange = { email = it },
                    leadingIcon = Icons.Default.Email,
                    leadingIconDescription = "Email"
                )

                Spacer(modifier = Modifier.height(16.dp))


                var password by remember { mutableStateOf("") }
                TextInput(
                    text = password,
                    placeholder = "Password",
                    onTextChange = { password = it },
                    isPassword = true,
                    leadingIcon = Icons.Default.Lock,
                    leadingIconDescription = "Password"
                )
                Spacer(modifier = Modifier.height(16.dp))

                GreenButton(text = "Done", onClick ={} )

                TextButton(onClick = { /*TODO*/ },
                ) {
                    Text(text = "Cancel",

                        fontSize = 15.sp,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Light,
                    )


                }



            }

        }
}





@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String,
    onTextChange: (String) -> Unit,
    isPassword: Boolean = false,
    leadingIcon: ImageVector ?= null,
    leadingIconDescription: String = "Icon"
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = text,
        onValueChange = onTextChange,
        placeholder = { Text(placeholder) },
        shape = CircleShape,
        modifier = modifier.fillMaxWidth(0.90f),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Gray,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            leadingIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = leadingIconDescription
                )
            }
        },
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            }
        } else null,
        visualTransformation = if (isPassword && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        keyboardOptions = if (isPassword) {
            KeyboardOptions(keyboardType = KeyboardType.Password)
        } else {
            KeyboardOptions.Default
        }
    )
}



@Preview(showBackground = true,showSystemUi = true)
@Composable
fun PreviewSignup() {
    SignUp(navController = rememberNavController())



}