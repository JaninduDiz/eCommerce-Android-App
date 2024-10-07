package com.example.shoppingapp.views

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.models.Review
import com.example.shoppingapp.models.User
import com.example.shoppingapp.utils.RetrofitInstance
import com.example.shoppingapp.viewmodels.VendorState
import com.example.shoppingapp.views.components.CustomTopAppBar
import com.example.shoppingapp.views.components.ShimmerComponents.ShimmerProductItem
import com.example.shoppingapp.views.components.ShimmerComponents.ShimmerRatingItem
import com.example.shoppingapp.views.components.ShimmerComponents.ShimmerVendorInfo
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VendorScreen(
    navController: NavController,
    vendorId: String,
    vendorState: VendorState
) {
    val scope = rememberCoroutineScope()
    var vendor by remember { mutableStateOf(vendorState.vendorDetails[vendorId]) } // Cached vendor details
    var vendorProducts by remember { mutableStateOf(vendorState.products[vendorId] ?: emptyList()) } // Cached products
    var vendorReviews by remember { mutableStateOf(vendorState.ratings[vendorId] ?: emptyList()) } // Cached ratings
    var loading by remember { mutableStateOf(vendor == null || vendorProducts.isEmpty() || vendorReviews.isEmpty()) }
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    // Fetch vendor details, products, and ratings only if not cached
    LaunchedEffect(vendorId) {
        if (vendor == null || vendorProducts.isEmpty() || vendorReviews.isEmpty()) {
            scope.launch {
                try {
                    // Fetch vendor details (as a User)
                    if (vendor == null) {
                        val vendorResponse = RetrofitInstance.api.getUserById(vendorId)
                        if (vendorResponse.isSuccessful) {
                            vendor = vendorResponse.body()
                            vendorState.vendorDetails[vendorId] = vendor // Cache vendor details
                        }
                    }

                    // Fetch vendor products
                    if (vendorProducts.isEmpty()) {
                        val productsResponse = RetrofitInstance.api.getVendorProducts(vendorId)
                        if (productsResponse.isSuccessful) {
                            vendorProducts = productsResponse.body() ?: emptyList()
                            vendorState.products[vendorId] = vendorProducts // Cache products
                        }
                    }

                    // Fetch vendor ratings
                    if (vendorReviews.isEmpty()) {
                        val ratingsResponse = RetrofitInstance.api.getRatingsByVendorId(vendorId)
                        if (ratingsResponse.isSuccessful) {
                            vendorReviews = ratingsResponse.body() ?: emptyList()
                            vendorState.ratings[vendorId] = vendorReviews // Cache ratings
                        }
                    }

                    loading = false // Data fetched and cached, disable loading
                } catch (e: Exception) {
                    Log.e("VendorScreen", "Error fetching vendor details: ${e.message}")
                    loading = false
                }
            }
        } else {
            loading = false // Data is already cached
        }
    }

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
                // Bottom bar content, if any
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(paddingValues)
                    .offset(y = (-26).dp)
            ) {
                VendorInfoSection(vendor = vendor, loading = loading)

                Spacer(modifier = Modifier.height(8.dp))

                if (!loading) {
                    // Tabs for Products and Reviews
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Tab(
                            selected = selectedTabIndex == 0,
                            onClick = { selectedTabIndex = 0 },
                            text = { Text("Products") }
                        )
                        Tab(
                            selected = selectedTabIndex == 1,
                            onClick = { selectedTabIndex = 1 },
                            text = { Text("Reviews") }
                        )
                    }

                    // Tab content
                    when (selectedTabIndex) {
                        0 -> {
                            ProductList(
                                vendorId = vendorId,
                                vendorState = vendorState,
                                onProductClick = { product ->
                                    navController.navigate("productDetails/${product.productId}")
                                }
                            )
                        }
                        1 -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                items(vendorReviews) { review ->
                                    ReviewItem(review = review, vendorState = vendorState)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VendorInfoSection(vendor: User?, loading: Boolean) {
    if (loading) {
        ShimmerVendorInfo()
    } else {
        vendor?.let {
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Text(
                    text = "${vendor.firstName.orEmpty()} ${vendor.lastName.orEmpty()}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Email: ${vendor.email}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                vendor.address?.let {
                    Text(
                        text = "Address: $it",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                vendor.phoneNumber?.let {
                    Text(
                        text = "Phone: $it",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductList(
    vendorId: String,
    vendorState: VendorState,
    onProductClick: (Product) -> Unit
) {
    // Fetch cached products
    val products = remember(vendorState.products[vendorId]) {
        vendorState.products[vendorId] ?: emptyList()
    }
    val isLoading = products.isEmpty()

    if (isLoading) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(2) {
                ShimmerProductItem()
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize().padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)  // Add vertical spacing between items
        ) {
            items(products) { product ->
                ProductCard(product = product, onClick = { onProductClick(product) })
            }
        }
    }
}

@Composable
fun ReviewItem(review: Review, vendorState: VendorState) {
    var customerName by remember { mutableStateOf(vendorState.customerNames[review.customerId]) }
    var isLoading by remember { mutableStateOf(customerName == null) }

    LaunchedEffect(review.customerId) {
        if (customerName == null) {
            val name = getCustomerName(review.customerId)
            customerName = name
            vendorState.customerNames[review.customerId] = name // Cache customer name
            isLoading = false
        } else {
            isLoading = false
        }
    }

    if (isLoading) {
        ShimmerRatingItem()
    } else {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StarRate(rating = review.stars)
                Text(
                    text = "by $customerName",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Text(
                text = review.comment,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 4.dp)
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun StarRate(rating: Int) {
    Row(horizontalArrangement = Arrangement.Center) {
        (1..5).forEach { index ->
            Icon(
                imageVector = if (index <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = "Star $index",
                tint = if (index <= rating) Color(0xFFFFD700) else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

suspend fun getCustomerName(userId: String): String {
    return try {
        val response = RetrofitInstance.api.getUserById(userId)
        if (response.isSuccessful && response.body() != null) {
            val user = response.body()!!
            "${user.firstName.orEmpty()} ${user.lastName.orEmpty()}".trim()
        } else {
            "Unknown Customer"
        }
    } catch (e: Exception) {
        Log.e("getCustomerName", "Error fetching user details: ${e.message}")
        "Unknown Customer"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewVendorScreen() {
    val navController = rememberNavController()
    val vendorState = VendorState()
    VendorScreen(navController = navController, vendorId = "sampleVendorId", vendorState = vendorState)
}