package com.example.ktshw

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import kotlinx.serialization.Serializable


// Добрый день)
@Serializable
object WelcomeDestination

@Serializable
object LoginDestination

@Composable
fun App() {
    val isDarkTheme = isSystemInDarkTheme()
    val colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme = colorScheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = WelcomeDestination,
                modifier = Modifier.fillMaxSize()
            ) {
                composable<WelcomeDestination> {
                    WelcomeScreen(
                        onNavigateToLogin = {
                            navController.navigate(LoginDestination)
                        }
                    )
                }
                composable<LoginDestination> {
                    LoginScreen(
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(onNavigateToLogin: () -> Unit) {
    val context = LocalPlatformContext.current

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .crossfade(true)
            .build()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // Долго думал почему нет изображения , оказалось ссылка заканчивалась не на .jpeg
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data("https://abrakadabra.fun/uploads/posts/2021-12/1639048666_7-abrakadabra-fun-p-kot-sfinks-art-7.jpg")
                    .build(),
                imageLoader = imageLoader,
                contentDescription = "Travel Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.DarkGray)
                    }
                },
                error = {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Failed to load", color = Color.DarkGray)
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "TRAVEL",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                Text(
                    text = "Travel is good thing",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Button(
                onClick = onNavigateToLogin,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Get started")
            }
        }
    }
}

@Composable
fun LoginScreen(onBack: () -> Unit) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { /* Логика  */ },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Login")
        }

        TextButton(
            onClick = onBack,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Back")
        }
    }
}

@Composable
fun WelcomeScreenPreview() {
    MaterialTheme {
        Surface {
            WelcomeScreen(onNavigateToLogin = {})
        }
    }
}
// комментарий
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        Surface {
            LoginScreen(onBack = {})
        }
    }
}
