package com.example.shoppingapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.models.User
import com.example.shoppingapp.views.components.CustomTopAppBar
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.session.UserSessionManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun ProfileScreen(navController: NavController, userSessionManager: UserSessionManager) {
    val currentUser = userSessionManager.getUser() // Retrieve current user

    CustomTopAppBar(
        title = "Profile",
        onNavigationClick = { navController.popBackStack() },
        centeredHeader = true
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
    var isEditing by remember { mutableStateOf(true) } // To toggle between view and edit modes
    var userName by remember { mutableStateOf(currentUser?.userName ?: "") }
    var emailAddress by remember { mutableStateOf(currentUser?.emailAddress ?: "") }
    var addressLine1 by remember { mutableStateOf(currentUser?.addressLine1 ?: "") }
    var addressLine2 by remember { mutableStateOf(currentUser?.addressLine2 ?: "") }
    var city by remember { mutableStateOf(currentUser?.city ?: "") }
    var postalCode by remember { mutableStateOf(currentUser?.postalCode ?: "") }


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
                    value = emailAddress,
                    label = "Email Address",
                    isEditing = isEditing,
                    onValueChange = { emailAddress = it },

                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileTextField(
                    value = addressLine1,
                    label = "Address Line 1",
                    isEditing = isEditing,
                    onValueChange = { addressLine1 = it },

                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileTextField(
                    value = addressLine2,
                    label = "Address Line 2",
                    isEditing = isEditing,
                    onValueChange = { addressLine2 = it },

                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileTextField(
                    value = city,
                    label = "City",
                    isEditing = isEditing,
                    onValueChange = { city = it },

                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ProfileTextField(
                    value = postalCode,
                    label = "Postal Code",
                    isEditing = isEditing,
                    onValueChange = { postalCode = it },
                    isError = postalCode.any { !it.isDigit() },
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
            // Floating Action Button Container (for Save/Edit)
            ActionButtonContainer(
                isEditing = isEditing,
                onSaveClick = {
                    val updatedUser = User(
                        userName = userName,
                        emailAddress = emailAddress,
                        addressLine1 = addressLine1,
                        addressLine2 = addressLine2,
                        city = city,
                        postalCode = postalCode
                    )
                    userSessionManager.updateUser(updatedUser)
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
    isError: Boolean = false, // Boolean to indicate if there's an error
    keyboardType: KeyboardType = KeyboardType.Text // Specify the keyboard type
) {
    val commonModifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFFf8f7ff), shape = RoundedCornerShape(16.dp))
        .padding(vertical = 8.dp, horizontal = 16.dp) // Consistent padding for both modes

    if (isEditing) {
        // When in editing mode, show the OutlinedTextField
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            enabled = true,  // Enable input
            singleLine = true, // Single line input
            isError = isError, // Set isError directly
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent, shape = RoundedCornerShape(16.dp)),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType), // Apply the correct KeyboardOptions
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFf8f7ff),
                unfocusedContainerColor = Color(0xFFf8f7ff),
                focusedBorderColor = Color(0xFF9381ff),
                unfocusedBorderColor = Color(0xFFf8f7ff),
                cursorColor = Color(0xFF9381ff),
            )
        )
    } else {
        // When not editing, show the Text view with the same modifier for consistent size
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
            .fillMaxWidth() // Ensures the FAB is aligned correctly in its parent
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd // Align the FAB to the bottom end
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
        val mockUser = User(userName = "Mark Adam", emailAddress = "markadam@hotmail.com", addressLine1 = null, addressLine2 = null, city = null, postalCode = null)
        val userSessionManager = UserSessionManager(context = LocalContext.current) // Mock instance
        userSessionManager.saveUser(mockUser) // Simulate user being logged in
        ProfileScreen(navController = navController, userSessionManager = userSessionManager)
    }
}