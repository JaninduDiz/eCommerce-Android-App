package com.example.shoppingapp.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.utils.RetrofitInstance
import com.example.shoppingapp.viewmodels.CartState
import com.example.shoppingapp.viewmodels.CategoryState
import com.example.shoppingapp.viewmodels.VendorState
import com.example.shoppingapp.views.components.CustomTopAppBar
import com.example.shoppingapp.views.components.ShimmerComponents.ShimmerBuyNowSection
import com.example.shoppingapp.views.components.ShimmerComponents.ShimmerProductImageCarousel
import com.example.shoppingapp.views.components.ShimmerComponents.ShimmerProductInfoSection


// Product Details Screen
@Composable
fun ProductDetailsScreen(
    navController: NavHostController,
    productId: String,
    cartState: CartState,
    vendorState: VendorState,
    categoryState: CategoryState
) {
    val context = LocalContext.current  // Get the current context
    var product by remember { mutableStateOf<Product?>(null) }
    var loading by remember { mutableStateOf(false) }

    val category = categoryState.categories.find { it.id == product?.category }
    val categoryName = category?.name ?: ""

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
        title = "Product Details",
        onNavigationClick = {
            navController.popBackStack()
            vendorState.clearVendorState() },
        centeredHeader = true,
        showActionIcon = true,
        isHeaderPinned = true,
    ) { paddingValues ->
        Scaffold(
            bottomBar = {
                if (loading) {
                    ShimmerBuyNowSection()
                } else {
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
                                onClick = {
                                    cartState.addToCart(product!!)
                                    Toast.makeText(
                                        context,
                                        "${product!!.name} added to cart",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val totPrice = cartState.getTotalAmount()
                                    navController.navigate("checkoutScreen/${totPrice}")
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFFffbf69
                                    )
                                )
                            ) {
                                Text(text = "Buy Now", color = Color.Black)
                            }
                            IconButton(
                                onClick = {
                                    cartState.addToCart(product!!)
                                    Toast.makeText(
                                        context,
                                        "${product!!.name} added to cart",
                                        Toast.LENGTH_SHORT
                                    ).show()
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
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
                    .offset(y = (-16).dp)
            ) {
                // Loading state
                if (loading) {
                    ShimmerProductImageCarousel()
                    Spacer(modifier = Modifier.height(16.dp))
                    ShimmerProductInfoSection()
                    Spacer(modifier = Modifier.height(16.dp))
                } else {
                    // Product details
                    if (product != null) {
                        ProductImageCarousel(product!!.imageUrls)
                        Spacer(modifier = Modifier.height(16.dp))
                        ProductInfoSection(product!!, categoryName)
                        Spacer(modifier = Modifier.height(16.dp))
                        product?.vendorId?.let { vendorId ->
                            SeeVendorDetails(vendorId, navController)
                        }
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

// Composable functions for Product Details Screen
// Product Image Carousel
@Composable
fun ProductImageCarousel(images: List<String>) {
    var currentIndex by remember { mutableIntStateOf(0) } // To track the current image index

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),

        contentAlignment = Alignment.Center
    ) {
        // Card to hold the image
        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(images[currentIndex]),
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Left Arrow Button
        IconButton(
            onClick = {
                if (currentIndex > 0) {
                    currentIndex--
                }
            },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Previous Image",
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }

        // Right Arrow Button
        IconButton(
            onClick = {
                if (currentIndex < images.size - 1) {
                    currentIndex++
                }
            },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Next Image",
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }

        // Image Count Indicator
        Text(
            text = "${currentIndex + 1} / ${images.size}",
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}


// Product Info Section
@Composable
fun ProductInfoSection(product: Product, categoryName: String = "") {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(0.7f)
            )
            Text(
                text = "$${product.price}",
                fontSize = 22.sp,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.weight(0.3f),
                textAlign = TextAlign.End,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Category: $categoryName",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(0.7f),
            )

            if (product.stock < 10) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Low Stock!",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Red,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(0.3f),
                )
            }
        }
    }
}

// Product Description Section
@Composable
fun ProductDescriptionSection(description: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Description",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
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

// See Vendor Details
@Composable
fun SeeVendorDetails(vendorId: String, navController: NavHostController) {
    Column {
        Text(
            text = "See Vendor >>>",
            color = Color(0xFF023e8a),
            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
            modifier = Modifier.clickable {
                navController.navigate("vendorDetails/${vendorId}")
            }
        )
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
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    ShoppingAppTheme {
        ProductDetailsScreen(navController = rememberNavController(), productId = "1", cartState = CartState(), vendorState = VendorState(), categoryState = CategoryState())
    }
}