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

@Composable
fun ProfileScreen(navController: NavController) {
    CustomTopAppBar(
        title = "Profile",
        onNavigationClick = { navController.popBackStack() },
        centeredHeader = true
    ) { paddingValues ->
            ProfileContent(paddingValues, sampleUser) { /* Handle save */ }
    }
}

@Composable
fun ProfileContent(paddingValues: PaddingValues, user: User, onSave: (User) -> Unit) {
    // State to control whether we're in edit mode or not
    var isEditing by remember { mutableStateOf(false) }

    // States for the editable fields
    var userName by remember { mutableStateOf(user.userName) }
    var emailAddress by remember { mutableStateOf(user.emailAddress) }
    var addressLine1 by remember { mutableStateOf(user.addressLine1) }
    var addressLine2 by remember { mutableStateOf(user.addressLine2) }
    var city by remember { mutableStateOf(user.city) }
    var postalCode by remember { mutableStateOf(user.postalCode) }

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

        // User Information
        TextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Name") },
            readOnly = !isEditing,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = emailAddress,
            onValueChange = { emailAddress = it },
            label = { Text("Email Address") },
            readOnly = !isEditing,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = addressLine1,
            onValueChange = { addressLine1 = it },
            label = { Text("Address Line 1") },
            readOnly = !isEditing,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = addressLine2,
            onValueChange = { addressLine2 = it },
            label = { Text("Address Line 2") },
            readOnly = !isEditing,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City") },
            readOnly = !isEditing,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = postalCode,
            onValueChange = { postalCode = it },
            label = { Text("Postal Code") },
            readOnly = !isEditing,
            modifier = Modifier.fillMaxWidth()
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
            onClick = { /* Handle logout */ },
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
fun ProfileContentPreview() {
    ProfileContent(
        paddingValues = PaddingValues(),
        user = sampleUser,
        onSave = { }
    )
}