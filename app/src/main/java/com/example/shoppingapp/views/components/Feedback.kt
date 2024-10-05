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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StarRatingWithComment(
    rating: Int,  // Pass current rating
    comment: String,  // Pass current comment
    onRatingChanged: (Int) -> Unit,  // Callback to handle rating change
    onCommentChanged: (String) -> Unit  // Callback to handle comment change
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Star rating row
        Text(text = "Rate this Vendor", fontWeight = FontWeight.Bold, fontSize = 20.sp)
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
                            onRatingChanged(index)  // Update rating when clicked
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Comment input field
        OutlinedTextField(
            value = comment,  // Set current comment
            onValueChange = {
                onCommentChanged(it)  // Update the comment when it changes
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
            maxLines = 4
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StarRatingWithCommentPreview() {
    StarRatingWithComment(
        onRatingChanged = { /* Handle rating change */ },
        onCommentChanged = { /* Handle comment change */ },
        rating = 3,
        comment = "This is a sample comment"
    )
}