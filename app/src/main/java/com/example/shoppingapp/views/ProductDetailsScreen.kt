package com.example.shoppingapp.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.utils.RetrofitInstance
import com.example.shoppingapp.viewmodels.CartState
import com.example.shoppingapp.views.components.CircularIndicator
import com.example.shoppingapp.views.components.CustomTopAppBar

@Composable
fun ProductDetailsScreen(
    navController: NavHostController,
    productId: String,
    cartState: CartState
) {
    val context = LocalContext.current  // Get the current context
    var product by remember { mutableStateOf<Product?>(null) }
    var loading by remember { mutableStateOf(false) }

    // Fetch product data using LaunchedEffect
    LaunchedEffect(productId) {
        if (productId.isNotEmpty()) {
            try {
                loading = true
                val response = RetrofitInstance.api.getProductById(productId)
                if (response.isSuccessful) {
                    product = response.body()
                    loading = false
                }
            } catch (e: Exception) {
                loading = false
                Log.e("ProductDetailsScreen", "Error fetching product: ${e.message}")
            }
        }
    }

    // Custom Top App Bar with product name if available
    CustomTopAppBar(
        title = product?.name ?: "Loading...",  // Handle null product case
        onNavigationClick = { navController.popBackStack() },
        centeredHeader = true,
        showActionIcon = true,
        isHeaderPinned = true,
        actionIcon = {
            IconButton(onClick = { /* Handle favorite click */ }) {
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite"
                )
            }
        }
    ) { paddingValues ->
        Scaffold(
            bottomBar = {
                if (product != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { /* Handle Buy Now */ },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCB997E))
                        ) {
                            Text(text = "Buy Now")
                        }
                        IconButton(
                            onClick = {
                                cartState.addToCart(product!!)
                                Toast.makeText(context, "${product!!.name} added to cart", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = "Add to Cart"
                            )
                        }
                    }
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
                // Loading state
                if (loading) {
                    CircularIndicator()
                } else {
                    // Product details
                    if (product != null) {
                        ProductImageSection(product!!.imageUrls[0])
                        Spacer(modifier = Modifier.height(16.dp))
                        ProductInfoSection(product!!, navController)
                        Spacer(modifier = Modifier.height(16.dp))
                        product?.description?.let { description ->
                            ProductDescriptionSection(description)
                        }
                    } else {
                        // Product not found or error state
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
            }
        }
    }
}


@Composable
fun ProductImageSection(imageURL: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageURL),
            contentDescription = "Product Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ProductInfoSection(product: Product, navController: NavHostController) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Row (modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
            text = product.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color(0xFF5e3023),
                    fontWeight = FontWeight.SemiBold
                )
            ) }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = "4.5",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "(20 Reviews)",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Category: ${product.category}",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Vendor: Isuru Stores",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray,
            modifier = Modifier.clickable {
                navController.navigate("vendorDetails/${product.vendorId}")
                Toast.makeText(context, "Vendor clicked", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
fun ProductDescriptionSection(description: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Description",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    ShoppingAppTheme {
        ProductDetailsScreen(navController = rememberNavController(), productId = "1", cartState = CartState())
    }
}