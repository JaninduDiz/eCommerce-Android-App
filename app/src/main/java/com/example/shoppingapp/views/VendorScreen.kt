package com.example.shoppingapp.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.models.sampleProducts
import com.example.shoppingapp.views.components.CustomTopAppBar

@Composable
fun VendorScreen(
    navController: NavController,
    vendorId: String,
) {
    val vendorProducts = sampleProducts.filter { it.vendorId == vendorId }

    CustomTopAppBar(
        title = "Vendor",
        onNavigationClick = {
            navController.popBackStack()
        },
        centeredHeader = true,
        isHeaderPinned = true
    ) { paddingValues ->
        Scaffold(
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 28.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                }
            }
        ) { contentPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Text(
                        text = "Vendor: ${vendorProducts.firstOrNull()?.vendorId ?: "Unknown"}",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = "Rating: 4.5", // Replace with actual rating if available
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VendorScreenPreview() {
    VendorScreen(navController = rememberNavController(), vendorId = "vendor3")
}