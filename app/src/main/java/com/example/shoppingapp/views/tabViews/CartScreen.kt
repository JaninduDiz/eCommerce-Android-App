package com.example.shoppingapp.views.tabViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.R
import com.example.shoppingapp.viewmodels.CartState
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.viewmodels.OrderState

@Composable
fun CartScreen(
    navController: NavController,
    cartState: CartState,
    orderState: OrderState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Cart",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (cartState.items.isNotEmpty()) {
                IconButton(
                    onClick = { cartState.clearCart() },
                    modifier = Modifier
                        .background(Color(0xFFB7B7A4), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Clear Cart",
                        tint = Color.Red,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }

        // Display each item in the cart
        cartState.items.forEach { cartItem ->
            CartItemRow(
                imageRes = R.drawable.ic_launcher_background,  // Replace with actual product image resource
                name = cartItem.product.name,
                brand = cartItem.product.category.name,
                price = "$${cartItem.product.price}",
                quantity = cartItem.quantity.value,
                onIncreaseClick = { cartState.increaseQuantity(cartItem.product) },
                onDecreaseClick = { cartState.decreaseQuantity(cartItem.product) },
                onRemoveClick = { cartState.removeFromCart(cartItem.product) }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (cartState.items.isNotEmpty()) {
            // Total Price Calculation
            val totalPrice = cartState.items.sumOf { it.product.price.toInt() * it.quantity.value }
            Text(
                text = "Total: $$totalPrice",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    // Generate an order using the OrderState
                    val order = orderState.generateOrder(cartState, customerId = "customer_123")

                    // Navigate to the CheckoutScreen and pass the order and total price
                    navController.navigate("checkoutScreen/$totalPrice")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B705C)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Checkout", color = Color.White, fontSize = 18.sp)
            }
        } else {
            Button(
                onClick = { /* Handle empty cart action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C6644)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Your cart is empty", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun CartItemRow(
    imageRes: Int,
    name: String,
    brand: String,
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
            .background(Color(0xFFF1F1F1), shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Product Image
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Product Name and Brand
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = brand, fontSize = 14.sp, color = Color.Gray)
            Text(text = price, fontSize = 16.sp, color = Color(0xFF6B705C))
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
        CartScreen(navController = rememberNavController(), cartState = CartState(), orderState = OrderState())
    }
}