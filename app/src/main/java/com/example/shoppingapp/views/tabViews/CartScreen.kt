package com.example.shoppingapp.views.tabViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.viewmodels.CartState
import com.example.shoppingapp.viewmodels.CategoryState
import com.example.shoppingapp.views.components.CustomButton

@Composable
fun CartScreen(
    navController: NavController,
    cartState: CartState,
    paddingValues: PaddingValues,
    categoryState: CategoryState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Top section: Cart Title and Clear Button (Fixed at the top)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp), // Padding to separate from the edges
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Cart",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )

                if (cartState.items.isNotEmpty()) {
                    IconButton(
                        onClick = { cartState.clearCart() },
                        modifier = Modifier.size(24.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Clear Cart",
                            tint = Color.Red,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // Scrollable content: Cart Items
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) 
            ) {
                items(cartState.items) { cartItem ->
                    CartItemRow(
                        imageUrl = cartItem.product.imageUrls.first(),
                        name = cartItem.product.name,
                        price = "$${cartItem.product.price}",
                        quantity = cartItem.quantity.value,
                        onIncreaseClick = { cartState.increaseQuantity(cartItem.product) },
                        onDecreaseClick = { cartState.decreaseQuantity(cartItem.product) },
                        onRemoveClick = { cartState.removeFromCart(cartItem.product) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (cartState.items.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),

                        ) {
                            Text(
                                text = "Your cart is empty",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }

        // Bottom section: Checkout Button (Fixed at the bottom)
        if (cartState.items.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                val totalPrice = cartState.getTotalAmount()

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Total: $$totalPrice",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.Start)
                    )

                    // Checkout button
                    CustomButton(
                        onClick = {
                            navController.navigate("checkoutScreen/$totalPrice")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF52b788)),
                        modifier = Modifier.fillMaxWidth(),
                        text = "Checkout"
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    imageUrl: String,
    name: String,
    price: String,
    quantity: Int,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Product Image
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = name,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Product Name and Brand
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = price, fontSize = 12.sp, color = Color(0xFF6B705C))
        }

        // Quantity Control
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onDecreaseClick) {
                Text(text = "-", fontSize = 30.sp)
            }
            Text(text = "$quantity", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = onIncreaseClick) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Increase Quantity")
            }
        }

        // Delete Button
        IconButton(onClick = onRemoveClick) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Item", tint = Color.Red)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    ShoppingAppTheme {
        CartScreen(navController = rememberNavController(), cartState = CartState(), paddingValues = PaddingValues(0.dp), categoryState = CategoryState())
    }
}