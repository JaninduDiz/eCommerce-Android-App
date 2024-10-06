package com.example.shoppingapp.views

import CustomModalBottomSheet
import android.content.ContentValues.TAG
import android.util.Log
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.models.ReviewRequest
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.utils.RetrofitInstance
import com.example.shoppingapp.utils.UserSessionManager
import com.example.shoppingapp.viewmodels.ProductState
import com.example.shoppingapp.views.components.CustomButton
import com.example.shoppingapp.views.components.CustomModal
import com.example.shoppingapp.views.components.CustomTopAppBar
import com.example.shoppingapp.views.components.ModalType
import com.example.shoppingapp.views.components.StarRatingWithComment
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    navController: NavHostController,
    productId: String?,
    vendorId: String?,
    productState: ProductState
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userSessionManager = UserSessionManager(LocalContext.current)
    val currentUser = userSessionManager.getUser()

    var product by remember { mutableStateOf<Product?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var showModal by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(true) }
    var rating by remember { mutableIntStateOf(0) }
    var comment by remember { mutableStateOf("") }

    // Check if product is cached in ProductState
    productId?.let { id ->
        val cachedProduct = productState.products.find { it.productId == id }
        if (cachedProduct != null) {
            product = cachedProduct
            loading = false
            Log.d(TAG, "ReviewScreen: Product found in cache")
        } else {
            LaunchedEffect(id) {
                try {
                    loading = true
                    Log.d(TAG, "ReviewScreen: Fetching product from API")
                    val response = RetrofitInstance.api.getProductById(id)
                    if (response.isSuccessful) {
                        product = response.body()
                    }
                } catch (e: Exception) {
                    Log.e("ReviewScreen", "Error fetching product: ${e.message}")
                } finally {
                    loading = false
                }
            }
        }
    }

    product?.let {
        CustomTopAppBar(
            title = "Review ${product!!.name}",
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
                            .padding(horizontal = 20.dp, vertical = 28.dp),
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
            ) { contentPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    ProductImageSection(product!!.imageUrls[0])
                    Spacer(modifier = Modifier.height(16.dp))
                    ProductInfoSection(product!!)
                    Spacer(modifier = Modifier.height(16.dp))
                    product!!.description?.let { it1 -> ProductDescriptionSection(it1) }
                }

                if (showDialog) {
                    CustomModal(
                        type = ModalType.ERROR,
                        title = "Oops!",
                        text = "Failed to submit review. Please try again.",
                        primaryButtonText = "OK",
                        onPrimaryButtonClick = {
                            showDialog = false
                            showModal = false
                        },
                        primaryButtonStyle = ButtonDefaults.buttonColors(containerColor = Color(0xFFc75146)),
                        secondaryButtonText = null,
                        tertiaryButtonText = null
                    )
                }

                if (showModal) {
                    CustomModalBottomSheet(
                        onDismiss = { showModal = false },
                        sheetContent = {
                            StarRatingWithComment(
                                rating = rating,
                                comment = comment,
                                onRatingChanged = { newRating ->
                                    rating = newRating
                                },
                                onCommentChanged = { newComment ->
                                    comment = newComment
                                }
                            )
                        },
                        buttonText = "Submit",
                        onButtonClick = {
                            val reviewRequest = ReviewRequest(
                                vendorId = vendorId!!,
                                customerId = currentUser.id,
                                stars = rating,
                                comment = comment
                            )
                            scope.launch {
//                                submitReview(reviewRequest).let { status ->
//                                    if (status == ApiResponseStatus.SUCCESS) {
//                                        Toast.makeText(context, "Review submitted successfully", Toast.LENGTH_SHORT).show()
//                                        Log.d("ReviewScreen", "Review submitted successfully")
//                                    } else {
//                                        showDialog = true
//                                        Log.e("ReviewScreen", "Failed to submit review")
//                                    }
//                                }
                                showModal = false
                            }
                            showModal = false
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

//TODO: Implement submitReview function
//suspend fun submitReview(
//    reviewRequest: ReviewRequest
//): ApiResponseStatus {
//    return try {
//        val response = RetrofitInstance.api.addRating(reviewRequest)
//        Log.d(TAG, "submitReview: Response: ${response.body()}") // Log the raw response string
//
//        if (response.isSuccessful) {
//            ApiResponseStatus.SUCCESS
//        } else {
//            Log.e("submitReview", "Failed to submit review: ${response.errorBody()?.string()}")
//            ApiResponseStatus.ERROR
//        }
//    } catch (e: Exception) {
//        Log.e("submitReview", "Error submitting review: ${e.message}")
//        ApiResponseStatus.ERROR
//    }
//}


@Preview(showBackground = true)
@Composable
fun ReviewScreenPreview() {
    ShoppingAppTheme {
        ReviewScreen(navController = rememberNavController(), productId = "1", vendorId = "1", productState = ProductState())
    }
}