package com.example.shoppingapp.views.components.ShimmerComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerRatingItem() {
    // Shimmer effect for the entire content while loading
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shimmer() // Apply shimmer effect
    ) {
        // Simulate stars
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Fake shimmer for stars
            repeat(5) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.Transparent), // The Box is transparent
                    contentAlignment = Alignment.Center // Align the Icon in the center
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = Color.LightGray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Shimmer for the customer name
            Spacer(
                modifier = Modifier
                    .width(120.dp)
                    .height(16.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Shimmer for the comment
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
fun ShimmerVendorInfo() {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .shimmer()
                .background(Color.Gray.copy(alpha = 0.6f), shape = RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.6f) // Partial width shimmer
                .height(24.dp)
                .shimmer()
                .background(Color.Gray.copy(alpha = 0.6f), shape = RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(16.dp))

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .shimmer()
                .background(Color.Gray.copy(alpha = 0.6f), shape = RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .shimmer()
                .background(Color.Gray.copy(alpha = 0.6f), shape = RoundedCornerShape(4.dp))
        )
    }
}

@Composable
fun ShimmerProductItem() {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .shimmer(),  // Apply shimmer effect
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray) // Placeholder for image
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.8f)  // Placeholder for product name, taking 80% width
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .height(14.dp)
                    .fillMaxWidth(0.4f)  // Placeholder for price, taking 40% width
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                    .background(Color.LightGray)
            )
        }
    }
}

@Composable
fun OrderShimmeringPlaceholder() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shimmer(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(20.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(14.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(16.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(18.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
            )
        }
    }
}

@Composable
fun ShimmerProductImageCarousel() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .shimmer(),  // Applying the shimmer effect
        contentAlignment = Alignment.Center
    ) {
        // Card to mimic the image placeholder
        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray) // Placeholder color for the shimmer effect
            )
        }
    }
}

@Composable
fun ShimmerProductInfoSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Placeholder for product name
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(30.dp)
                .background(Color.LightGray.copy(alpha = 0.5f))
                .shimmer()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Placeholder for product price
        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(20.dp)
                .background(Color.LightGray.copy(alpha = 0.5f))
                .shimmer()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Placeholder for category name
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(20.dp)
                .background(Color.LightGray.copy(alpha = 0.5f))
                .shimmer()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Placeholder for category name
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(20.dp)
                .background(Color.LightGray.copy(alpha = 0.5f))
                .shimmer()
        )
    }
}

@Composable
fun ShimmerBuyNowSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Placeholder for the "Buy Now" button
        Box(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .background(Color.LightGray.copy(alpha = 0.5f), shape = RoundedCornerShape(18.dp))
                .shimmer()
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Placeholder for the "Add to Cart" icon button
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color.LightGray.copy(alpha = 0.5f), shape = CircleShape)
                .shimmer()
        )
    }
}
