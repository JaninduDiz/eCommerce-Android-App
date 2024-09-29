package com.example.shoppingapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.shoppingapp.models.sampleUser
import com.example.shoppingapp.views.components.CustomTopAppBar
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.session.UserSessionManager
import com.example.shoppingapp.models.User
import androidx.compose.ui.platform.LocalContext

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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

        // User Name
        Text(
            text = currentUser?.userName ?: "Unknown User", // Show username or fallback
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black

        )

        Spacer(modifier = Modifier.height(8.dp))


        // User Email
        Text(
            text = currentUser?.emailAddress ?: "No Email", // Show email or fallback
            fontSize = 16.sp,
            color = Color.Gray

        )

        Spacer(modifier = Modifier.height(8.dp))


        // User Joined Date
        Text(
            text = "Joined: January 15, 2022", // This can be updated to show actual joined date if available
            fontSize = 16.sp,
            color = Color.Gray

        )

        Spacer(modifier = Modifier.weight(1f))

        // Edit/Save Button
        FloatingActionButton(
            onClick = {
                if (isEditing) {
                    // Save the changes
                    onSave(
                        User(
                            userName = userName,
                            emailAddress = emailAddress,
                            addressLine1 = addressLine1,
                            addressLine2 = addressLine2,
                            city = city,
                            postalCode = postalCode
                        )
                    )
                }
                isEditing = !isEditing
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = if (isEditing) Icons.Filled.Done else Icons.Filled.Edit,
                contentDescription = if (isEditing) "Save" else "Edit"
            )
        }

        // Logout Button
        Button(
            onClick = {
                userSessionManager.logout() // Logout user
                navController.navigate("login") // Navigate back to login screen
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = "Sign Out", color = Color(0xFFb23a48), fontSize = 16.sp)
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
