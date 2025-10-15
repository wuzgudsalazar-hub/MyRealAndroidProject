package com.example.myandroidpracticeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myandroidpracticeproject.ui.theme.MyAndroidPracticeProjectTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import android.widget.Toast
// Forcing a save
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                    MyAndroidPracticeProjectTheme {

                        val navController = rememberNavController()


                        NavHost(navController = navController, startDestination = "login") {
                            // Defines the login screen route
                            composable("login") {
                                LoginScreen(navController = navController)
                            }

                            composable("welcome/{name}") { backStackEntry ->
                                val name = backStackEntry.arguments?.getString("name") ?: "User"
                                // --- FIX: Pass the navController here ---
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
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Welcome Back!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = username,
                onValueChange = { newText -> username = newText },
                label = { Text("Username") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = password,
                onValueChange = { newText -> password = newText },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = {

                    if (username.isNotEmpty() && password.isNotEmpty()) {

                        if (username == "sam" && password == "123") {
                            // If they are correct, navigate
                            navController.navigate("welcome/$username")
                        } else {
                            Toast.makeText(context, "Password dakpun username hang salah.", Toast.LENGTH_SHORT).show()
                        }
                    } else {

                        Toast.makeText(context, "Dawg, please isi semua.", Toast.LENGTH_SHORT).show()
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
// Manually edited in Notepad to force a save
