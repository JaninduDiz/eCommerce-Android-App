package com.example.shoppingapp.views

import CustomModalBottomSheet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.models.sampleProducts
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.views.components.CustomButton
import com.example.shoppingapp.views.components.CustomTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    navController: NavHostController,
    productId: String?,
) {
    val product = sampleProducts.find { it.productId == productId }
    var showModal by remember { mutableStateOf(false) }

    product?.let {
        CustomTopAppBar(
            title = "Review ${product.name}",
            onNavigationClick = { navController.popBackStack() },
            centeredHeader = true,
            showActionIcon = true,
            isHeaderPinned = true,
        ) { paddingValues ->
            Scaffold(
                bottomBar = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CustomButton(
                            text = "Review Vendor",
                            onClick = {
                                showModal = true
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        )
                    }
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    ProductImageSection()
                    Spacer(modifier = Modifier.height(16.dp))
                    ProductInfoSection(product)
                    Spacer(modifier = Modifier.height(16.dp))
                    ProductDescriptionSection(product.description)
                }

                if (showModal) {
                    // Show the bottom sheet with a simple text "Modal"
                    CustomModalBottomSheet(
                        onDismiss = { showModal = false },
                        sheetContent = {
                            Text(
                                text = "This is a modal",
                                modifier = Modifier.padding(16.dp)
                            )
                        },
                        buttonText = "Close",
                        onButtonClick = {
                            showModal = false // Close the modal when the button is pressed
                        }
                    )
                }
            }
        }
    } ?: run {
        Text(
            text = "Product not found",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ReviewScreenPreview() {
    ShoppingAppTheme {
        ReviewScreen(navController = rememberNavController(), productId = "1")
    }
}