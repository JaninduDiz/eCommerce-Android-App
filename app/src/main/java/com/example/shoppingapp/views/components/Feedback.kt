package com.example.shoppingapp.views.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StarRatingWithComment(
    onRatingChanged: (Int) -> Unit,  // Callback to handle rating change
    onCommentChanged: (String) -> Unit  // Callback to handle comment change
) {
    var rating by remember { mutableStateOf(0) }  // Track the selected star rating
    var comment by remember { mutableStateOf("") }  // Track the entered comment

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Star rating row
        Text(text = "Rate this Vendor", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            (1..5).forEach { index ->
                Icon(
                    imageVector = if (index <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "Star $index",
                    tint = if (index <= rating) Color(0xFFFFD700) else Color.Gray,
                    modifier = Modifier
                        .size(48.dp)
                        .clickable {
                            rating = index
                            onRatingChanged(rating)
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Comment input field
        OutlinedTextField(
            value = comment,
            onValueChange = {
                comment = it
                onCommentChanged(comment)  // Handle comment change
            },
            label = { Text("Enter your comment") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFf8f7ff),
                unfocusedContainerColor = Color(0xFFf8f7ff),
                focusedBorderColor = Color(0xFF9381ff),
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color(0xFF9381ff)
            ),
            maxLines = 4  // Allow multi-line comment input
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StarRatingWithCommentPreview() {
    StarRatingWithComment(
        onRatingChanged = { /* Handle rating change */ },
        onCommentChanged = { /* Handle comment change */ }
    )
}