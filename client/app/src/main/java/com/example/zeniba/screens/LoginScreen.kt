package com.example.zeniba.screens

import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Email

import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.zeniba.BuildConfig
import com.example.zeniba.data.AuthRepository
import com.example.zeniba.ui.theme.PoppinsFontFamily
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.gson.internal.GsonBuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch


@Composable
fun Login(onLoggedIn: () -> Unit){

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repo = remember { AuthRepository(context) }

    val webClientId = BuildConfig.WEB_CLIENT_ID

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(webClientId)
        .requestEmail()
        .build()

    val googleClient = GoogleSignIn.getClient(context, gso)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (!idToken.isNullOrBlank()) {
                scope.launch {
                    Log.d("Login", "Attempting to exchange token")
                    val success = repo.exchangeIdTokenForJwt(idToken)
                    if (success) {
                        Log.d("Login", "Login successful")
                        onLoggedIn()
                    } else {
                        Log.e("Login", "JWT exchange failed")
                    }
                }
            }
        } catch (e: ApiException) {
            Log.e("Login", "Google sign in failed", e)
            e.printStackTrace()
        }
    }


    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF0F0EE))
            .padding(20.dp, 10.dp, 10.dp, 10.dp),

        ) {

        Spacer(modifier = Modifier.fillMaxHeight(0.3f))

        Text(text ="Login",
            fontSize = 50.sp ,// use sp for text
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Bold,
            letterSpacing = -0.5.sp,
            lineHeight = 54.sp,

            )
        Text(text ="Good to see you back!",
            fontSize = 19.sp ,// use sp for text
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Light,
            letterSpacing = -0.5.sp,
            lineHeight = 54.sp,
            color = Color(0xFF202020)
        )
        Spacer(modifier = Modifier.height(28.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var selected by remember { mutableStateOf("+91") }
            val options = listOf("Option 1", "Option 2", "Option 3","Option 1", "Option 2", "Option 3","Option 1", "Option 2", "Option 3","Option 1", "Option 2", "Option 3","Option 1", "Option 2", "Option 3","Option 1", "Option 2", "Option 3","Option 1", "Option 2", "Option 3","Option 1", "Option 2", "Option 3","Option 1", "Option 2", "Option 3","Option 1", "Option 2", "Option 3","Option 1", "Option 2", "Option 3","Option 1", "Option 2", "Option 3")

            var phoneNum by remember { mutableStateOf("") }

            PhoneNumInput(

                text = phoneNum,
                onTextChange = { phoneNum = it },
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )


            Spacer(modifier = Modifier.height(15.dp))


            Text(text ="or",
                fontSize = 19.sp ,// use sp for text
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Light,
                letterSpacing = -0.5.sp,
                lineHeight = 54.sp,

                )

            Spacer(modifier = Modifier.height(14.dp))


            var email by remember { mutableStateOf("") }
            TextInput(text =email , placeholder = "Email",
                onTextChange ={email=it},
                modifier = Modifier.fillMaxWidth(0.95f),
                leadingIcon = Icons.Default.Email
                )

            Spacer(modifier = Modifier.height(37.dp))
            var isLoading by remember { mutableStateOf(false) }
            GreenButton(text = "Next", onClick = {

                launcher.launch(googleClient.signInIntent)




            },
                modifier =Modifier.fillMaxWidth(0.95f)
            )
            Spacer(modifier = Modifier.height(16.dp))

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
fun PhoneNumInput(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String = "Enter number",
    onTextChange: (String) -> Unit
) {
    var selected by remember { mutableStateOf("+91") }
    val options = listOf("+91", "+1", "+44", "+61")

    Row(
        verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth(0.95f)
            .height(55.dp)
            .background(Color.White, CircleShape)

    ) {
        // Country code dropdown
        BestDropdownMenu(
            options = options,
            selectedOption = selected,
            onOptionSelected = { selected = it },
            modifier = Modifier
                .width(75.dp)
                .padding(10.dp)// fixed width for codes
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Phone number text field
        TextField(
            value = text,
            onValueChange = onTextChange,
            placeholder = { Text(placeholder, fontFamily = PoppinsFontFamily, fontWeight = FontWeight.Medium) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.weight(1f), // take remaining width
            singleLine = true,
//            textStyle = TextStyle(
//                fontFamily = PoppinsFontFamily,
//                fontWeight = FontWeight.Medium
//                )

        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownTextInput(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    placeholder: String = "Select option"
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true, // user can't type
            placeholder = { Text(placeholder) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onOptionSelected(selectionOption)
                        expanded = false
                    }
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDropdownMenu(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Select option"
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.wrapContentSize(Alignment.TopStart)) {
        // OutlinedTextField works better for this use case
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { },
            readOnly = true,
            placeholder = { Text(placeholder) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                }
            }
        )

        // DropdownMenu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun BestDropdownMenu(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier
        .clickable { expanded = !expanded },
        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedOption,
                fontSize = 16.sp,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Medium
            )

            Icon(
                imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = null
            )
            Spacer(modifier = Modifier.weight(1f))
            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxHeight()   // full height of parent
                    .width(1.dp),

                // thin vertical line
            )

        }




        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(120.dp) // Fixed width instead of fillMaxWidth
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            fontSize = 14.sp
                        )
                    },
                    onClick = {
                        onOptionSelected(option) // Let parent handle state
                        expanded = false
                    }
                )
            }
        }
    }
}




@Composable
fun CancelButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit
){
    TextButton(onClick = onClick
    ) {
        Text(text = text,
            fontSize = 15.sp,
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Light,
        )



    }
}

private fun decodeAndLogToken(idToken: String) {
    try {
        val parts = idToken.split(".")
        if (parts.size == 3) {
            val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
            Log.d("TokenPayload", payload)
            Log.d("FullToken", idToken)
            // Look for "iat" (issued at) and "exp" (expiration) claims
        }
    } catch (e: Exception) {
        Log.e("TokenDecode", "Error decoding token", e)
    }
}

@Preview(showBackground = true, device = Devices.DEFAULT, showSystemUi = true)
@Composable
fun PreviewLogin() {

Login({})

}