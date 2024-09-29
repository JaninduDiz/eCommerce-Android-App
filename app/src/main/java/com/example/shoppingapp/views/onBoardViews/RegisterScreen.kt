package com.example.shoppingapp.views.onBoardViews

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.ui.theme.ShoppingAppTheme

@Composable
fun RegisterScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 6
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFf3f4f6)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Register",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3c3c3c)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            BasicTextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, MaterialTheme.shapes.small)
                    .padding(16.dp),
                textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (username.isEmpty()) {
                        Text(
                            text = "Username",
                            style = TextStyle(color = Color.Gray, fontSize = 18.sp)
                        )
                    }
                    innerTextField()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = !validateEmail(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, MaterialTheme.shapes.small)
                    .padding(16.dp),
                textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (email.isEmpty()) {
                        Text(
                            text = "Email",
                            style = TextStyle(color = Color.Gray, fontSize = 18.sp)
                        )
                    }
                    innerTextField()
                }
            )
            if (emailError) {
                Text(
                    text = "Invalid email format",
                    color = Color.Red,
                    style = TextStyle(fontSize = 14.sp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = !validatePassword(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, MaterialTheme.shapes.small)
                    .padding(16.dp),
                textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (password.isEmpty()) {
                        Text(
                            text = "Password",
                            style = TextStyle(color = Color.Gray, fontSize = 18.sp)
                        )
                    }
                    innerTextField()
                }
            )
            if (passwordError) {
                Text(
                    text = "Password must be at least 6 characters",
                    color = Color.Red,
                    style = TextStyle(fontSize = 14.sp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (!emailError && !passwordError) {
                        // Handle registration logic
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !emailError && !passwordError
            ) {
                Text(text = "Register")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Already have an account? Login",
                color = Color.Blue,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    ShoppingAppTheme {
        val navController = rememberNavController()
        RegisterScreen(navController = navController)
    }
}