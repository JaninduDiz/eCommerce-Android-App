package com.example.shoppingapp.views

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.ui.theme.ShoppingAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    navController: NavHostController,
    productId: String?,
) {
//    val product = sampleProducts.find { it.productId == productId }
//    var showModal by remember { mutableStateOf(false) }
//    var rating by remember { mutableIntStateOf(0) }  // Track the selected star rating
//    var comment by remember { mutableStateOf("") }  // Track the entered comment

//    product?.let {
//        CustomTopAppBar(
//            title = "Review ${product.name}",
//            onNavigationClick = { navController.popBackStack() },
//            centeredHeader = true,
//            showActionIcon = true,
//            isHeaderPinned = true,
//        ) { paddingValues ->
//            Scaffold(
//                bottomBar = {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(MaterialTheme.colorScheme.surface)
//                            .padding(horizontal = 20.dp, vertical = 28.dp),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        CustomButton(
//                            text = "Review Vendor",
//                            onClick = {
//                                showModal = true
//                            },
//                            modifier = Modifier
//                                .weight(1f)
//                                .padding(end = 8.dp)
//                        )
//                    }
//                }
//            ) { contentPadding ->
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(contentPadding)
//                        .padding(paddingValues)
//                        .verticalScroll(rememberScrollState())
//                        .padding(horizontal = 16.dp)
//                ) {
//                    ProductImageSection(product.imageUrls[0])
//                    Spacer(modifier = Modifier.height(16.dp))
//                    ProductInfoSection(product)
//                    Spacer(modifier = Modifier.height(16.dp))
//                    product.description?.let { it1 -> ProductDescriptionSection(it1) }
//                }
//
//                if (showModal) {
//                    CustomModalBottomSheet(
//                        onDismiss = { showModal = false },
//                        sheetContent = {
//                            StarRatingWithComment(
//                                rating = rating,  // Pass current rating
//                                comment = comment,  // Pass current comment
//                                onRatingChanged = { newRating ->
//                                    rating = newRating  // Update the rating
//                                },
//                                onCommentChanged = { newComment ->
//                                    comment = newComment  // Update the comment
//                                }
//                            )
//                        },
//                        buttonText = "Submit",
//                        onButtonClick = {
//                            showModal = false // Close the modal when the button is pressed
//                        }
//                    )
//                }
//            }
//        }
//    } ?: run {
//        Text(
//            text = "Product not found",
//            style = MaterialTheme.typography.bodyLarge,
//            color = MaterialTheme.colorScheme.error,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            textAlign = TextAlign.Center
//        )
//    }
}


@Preview(showBackground = true)
@Composable
fun ReviewScreenPreview() {
    ShoppingAppTheme {
        ReviewScreen(navController = rememberNavController(), productId = "1")
    }
}