package com.example.contacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contacts.ui.theme.ContactsTheme

class MainActivity : ComponentActivity() {
    val contacts = mutableStateListOf(
        Contact(
            0,
            "Мунирҷон",
            991113344,
            "Хуҷанд",
            "mmmmiii1997@gmail.com"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactsTheme {
                var navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(Color(0xFF0E0E18))
                    ) {


                        NavHost(
                            navController = navController,
                            startDestination = "Search",
                            modifier = Modifier.weight(1f)
                        ) {
                            composable("Search") { backstackEntry ->
                                Search(
                                    navController = navController,
                                    contacts = contacts
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

