package com.example.shoppingapp.views.tabViews

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.viewmodels.ProductState

@Composable
fun SearchScreen(navController: NavController, productState: ProductState, paddingValues: PaddingValues) {
    var searchQuery by remember { mutableStateOf("") }

    // Filter products based on the search query
    val filteredProducts = if (searchQuery.isEmpty()) {
        emptyList()
    } else {
        productState.products.filter {
            it.name.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues) // Only padding from the provided paddingValues
    ) {
        // Search bar pinned at the top, not scrollable
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(text = "Search products...") },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Clear search")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // Padding around the search bar
                .background(Color.Transparent, shape = RoundedCornerShape(16.dp)),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9381ff)
            )
        )

        // Scrollable content (search results)
        if (searchQuery.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Gray,
                        modifier = Modifier.size(80.dp)
                    )
                    Text(
                        text = "Start typing to search products",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp)
            ) {
                items(filteredProducts) { product ->
                    ProductCard(product = product, onClick = {
                        navController.navigate("productDetails/${product.productId}")
                    })

                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "$${product.price}", style = MaterialTheme.typography.bodySmall, color = Color(0xFF5e3023))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    val productState = remember { ProductState() }
    productState.addProducts(
        listOf(
            Product(
                productId = "1",
                name = "Leather Sofa",
                category = "Living Room",
                vendorId = "v1",
                isActive = true,
                price = 999.99,
                description = "Comfortable leather sofa",
                stock = 5,
                imageUrls = listOf("https://example.com/sofa.jpg")
            ),
            Product(
                productId = "2",
                name = "Wooden Chair",
                category = "Dining Room",
                vendorId = "v2",
                isActive = true,
                price = 149.99,
                description = "Elegant wooden chair",
                stock = 12,
                imageUrls = listOf("https://example.com/chair.jpg")
            )
        )
    )

    ShoppingAppTheme {
        SearchScreen(navController = rememberNavController(), productState = productState, paddingValues = PaddingValues(0.dp))
    }
}