package com.example.shoppingapp.views

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.models.User
import com.example.shoppingapp.session.UserSessionManager
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.utils.RetrofitInstance
import com.example.shoppingapp.views.components.CustomTopAppBar


@Composable
fun ProfileScreen(navController: NavController, userSessionManager: UserSessionManager) {
    val currentUser = userSessionManager.getUser() // Retrieve current user

    CustomTopAppBar(
        title = "Profile",
        onNavigationClick = { navController.popBackStack() },
        centeredHeader = true,
        isHeaderPinned = true
    ) { paddingValues ->
        ProfileContent(paddingValues, currentUser, userSessionManager, navController)
    }
}

@Composable
fun ProfileContent(
    paddingValues: PaddingValues,
    currentUser: User?,
    userSessionManager: UserSessionManager,
    navController: NavController
) {
    var loading by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var userName by remember { mutableStateOf(currentUser?.userName ?: "") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var address by remember { mutableStateOf(currentUser?.address ?: "") }
    var phoneNumber by remember { mutableStateOf(currentUser?.phoneNumber ?: "") }
    var firstName by remember { mutableStateOf(currentUser?.firstName ?: "") }
    var lastName by remember { mutableStateOf(currentUser?.lastName ?: "") }

    LaunchedEffect(Unit) {
        try {
            loading = true
            val response = currentUser?.let { RetrofitInstance.api.getUserById(it.id) }
            if (response != null) {
                if (response.isSuccessful) {
                    response.body()?.let { user ->
                        val loggedInUser = User(
                            id = user.id,
                            userName = user.userName,
                            email = user.email,
                            address = user.address,
                            phoneNumber = user.phoneNumber,
                            firstName = user.firstName,
                            lastName = user.lastName
                        )

                        userSessionManager.saveUser(loggedInUser)
                    }
                }
            }
        } catch (e: Exception) {
            loading = false
            Log.d(TAG, "HomeScreen: ${e.message}, error fetching categories")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        // Scrollable content with LazyColumn
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // User Avatar
                Image(
                    painter = rememberAsyncImagePainter("https://shorturl.at/IyCCJ"),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                // Profile Fields
                ProfileTextField(
                    value = userName,
                    label = "Username",
                    isEditing = isEditing,
                    onValueChange = { userName = it },

                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileTextField(
                    value = email,
                    label = "Email Address",
                    isEditing = isEditing,
                    onValueChange = { email = it },
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileTextField(
                    value = firstName,
                    label = "First Name",
                    isEditing = isEditing,
                    onValueChange = { firstName = it },
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileTextField(
                    value = lastName,
                    label = "Last Name",
                    isEditing = isEditing,
                    onValueChange = { lastName = it },

                    )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileTextField(
                    value = address,
                    label = "Address Line",
                    isEditing = isEditing,
                    onValueChange = { address = it },
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileTextField(
                    value = phoneNumber,
                    label = "Phone Number",
                    isEditing = isEditing,
                    onValueChange = { phoneNumber = it },
                    isError = phoneNumber.any { !it.isDigit() },
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Fixed buttons at the bottom of the screen
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter) // Align at the bottom center
                .padding(16.dp)
        ) {
            ActionButtonContainer(
                isEditing = isEditing,
                onSaveClick = {
                    val updatedUser = currentUser?.let {
                        User(
                            id = it.id,
                            userName = userName,
                            email = email,
                            address = address,
                            phoneNumber = phoneNumber,
                            lastName = lastName,
                            firstName = firstName,
                        )
                    }
                    if (updatedUser != null) {
                        userSessionManager.updateUser(updatedUser)          //TODO: Update user api call
                    }
                    isEditing = false
                },
                onEditClick = { isEditing = true }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Logout Button
            Button(
                onClick = {
                    userSessionManager.logout() // Logout user
                    navController.navigate("login") // Navigate back to login screen
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Sign Out", color = Color(0xFFb23a48), fontSize = 16.sp)
            }
        }
    }
    }

@Composable
fun ProfileTextField(
    value: String,
    label: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    if (isEditing) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            enabled = true,
            singleLine = true,
            isError = isError,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent, shape = RoundedCornerShape(16.dp)),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFf8f7ff),
                unfocusedContainerColor = Color(0xFFf8f7ff),
                focusedBorderColor = Color(0xFF9381ff),
                unfocusedBorderColor = Color(0xFFf8f7ff),
                cursorColor = Color(0xFF9381ff),
                errorBorderColor = Color.Red
            )
        )
    } else {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFf8f7ff), shape = RoundedCornerShape(10.dp))
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
            )
        }
    }
}

@Composable
fun ActionButtonContainer(
    isEditing: Boolean,
    onSaveClick: () -> Unit,
    onEditClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        ExtendedFloatingActionButton(
            onClick = {
                if (isEditing) onSaveClick() else onEditClick()
            },
            containerColor = Color(0xFFB08968),
            contentColor = Color.White
        ) {
            if (isEditing) {
                Text(text = "Save", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            } else {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Profile", modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Edit", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ShoppingAppTheme {
        val navController = rememberNavController()
        val mockUser = User(
            id = "1",
            userName = "Mark Adam",
            email = "markadam@hotmail.com",
            address = "123 Main St",
            phoneNumber = "1234567890",
            firstName = "Mark",
            lastName = "Adam"
        )
        val userSessionManager = UserSessionManager(context = LocalContext.current) // Mock instance
        userSessionManager.saveUser(mockUser) // Simulate user being logged in
        ProfileScreen(navController = navController, userSessionManager = userSessionManager)
    }
}