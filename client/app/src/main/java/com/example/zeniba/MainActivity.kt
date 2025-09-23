package com.example.zeniba

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.zeniba.navigation.AppNavigation
import com.example.zeniba.screens.CatagoryScreen

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate called")

        setContent {
            AppNavigation()
        }
    }
}




@Preview(showBackground = true,showSystemUi = true)
@Composable
fun GreetingPreview() {
}