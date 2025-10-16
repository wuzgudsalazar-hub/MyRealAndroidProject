package com.example.myandroidpracticeproject

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myandroidpracticeproject.ui.theme.MyAndroidPracticeProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAndroidPracticeProjectTheme {
                // 1. Create the navigation controller
                val navController = rememberNavController()

                // 2. Define the navigation map (NavHost)
                NavHost(navController = navController, startDestination = "login") {
                    // Defines the login screen route
                    composable("login") {
                        LoginScreen(navController = navController)
                    }
                    // Defines the welcome screen route with a "name" argument
                    composable("welcome/{name}") { backStackEntry ->
                        val name = backStackEntry.arguments?.getString("name") ?: "User"
                        // --- FIX: Call the WelcomeScreen function here ---
                        WelcomeScreen(name = name, navController = navController)
                    }
                    }
                }
            }
        }
    }


@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Logo Image ---
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- Title ---
        Text(
            text = "Welcome Back!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Username Text Field ---
        TextField(
            value = username,
            onValueChange = { newText -> username = newText },
            label = { Text("Username") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- Password Text Field ---
        TextField(
            value = password,
            onValueChange = { newText -> password = newText },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Login Button ---
        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    if (username == "admin" && password == "123") {
                        navController.navigate("welcome/$username")
                    } else {
                        Toast.makeText(context, "Invalid username or password.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}

@Composable
fun WelcomeScreen(name: String, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome, $name!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // --- Logout Button ---
        Button(onClick = {
            navController.navigate("login") {
                popUpTo("login") {
                    inclusive = true
                }
            }
        }) {
            Text("Logout")
        }
    }
}
