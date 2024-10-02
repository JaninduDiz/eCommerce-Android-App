package com.example.shoppingapp.views

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.R
import com.example.shoppingapp.models.Order
import com.example.shoppingapp.models.OrderItem
import com.example.shoppingapp.models.sampleOrders
import com.example.shoppingapp.models.sampleProducts
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.viewmodels.OrderState
import com.example.shoppingapp.views.components.CustomButton
import com.example.shoppingapp.views.components.CustomModal
import com.example.shoppingapp.views.components.CustomTopAppBar
import com.example.shoppingapp.views.components.ModalType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderDetailsScreen(navController: NavController, orderId: String, orderState: OrderState, isBackHome: Boolean? = false) {
    val order = sampleOrders[0] // orderState.getOrderById(orderId)
    var showModal by remember { mutableStateOf(false) }

    CustomTopAppBar(
        title = "Order Details",
        onNavigationClick = {
            if (isBackHome == true) navController.navigate("home")
            else navController.popBackStack()
        },
        centeredHeader = true,
        isHeaderPinned = true,
    ) { paddingValues ->
        order.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                item {
                    // Order status and date placed
                    Text(text = "Order ID: ${it.id}", fontWeight = FontWeight.Bold)
                    Text(text = "Date Placed: ${it.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MMM-dd @ hh:mm a"))}", fontWeight = FontWeight.Bold)
                    val (statusText, statusColor) = orderStatusText(it.status)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.Left
                    ) {
                        Text(text = "Status: ", fontWeight = FontWeight.Bold)
                        Text(text = statusText, fontWeight = FontWeight.Bold, color = statusColor)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(it.items) { orderItem ->
                    OrderItemCard(orderItem, navController)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    OrderSummary(
                        totalItems = it.items.size,
                        totalPrice = it.items.sumOf { item -> item.product.price * item.quantity }
                    )

                    Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))

                    // Cancel Order Button
                    CustomButton(
                        text = "Cancel Order",
                        onClick = { showModal = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFc75146))
                    )

                    if (showModal) {
                        CustomModal(
                            type = ModalType.ERROR,
                            title = "Cancel Order?",
                            text = "Are you sure you want to cancel this order?",
                            primaryButtonText = "Confirm",
                            onPrimaryButtonClick = {
                                showModal = false
                                //TODO: Implement cancel order logic
                            },
                            primaryButtonStyle = ButtonDefaults.buttonColors(containerColor = Color(0xFFc75146)),
                            secondaryButtonText = "Close",
                            onSecondaryButtonClick = { showModal = false },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun orderStatusText(status: Int): Pair<String, Color> {
    return when (status) {
        0 -> "Processing" to Color(0xFF4CAF50) // Green
        1 -> "Ready For Delivery" to Color(0xFFFFC107) // Amber
        2 -> "Shipped" to Color(0xFF2196F3) // Blue
        3 -> "Delivered" to Color(0xFF8BC34A) // Light Green
        4 -> "Cancelled" to Color(0xFFF44336) // Red
        else -> "Unknown" to Color(0xFF9E9E9E) // Grey
    }
}

@Composable
fun OrderItemCard(orderItem: OrderItem, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                Toast.makeText(navController.context, "Navigate to product details", Toast.LENGTH_SHORT).show()
                navController.navigate("reviewScreen/${orderItem.product.productId}")
            },
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with actual image
                contentDescription = "Product Image",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = orderItem.product.name, fontWeight = FontWeight.Bold)
                Text(text = "Quantity: ${orderItem.quantity}")
                Text(text = "Price: $${orderItem.product.price}")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun OrderDetailsScreenPreview() {
    val sampleOrder = Order(
        id = "order_1",
        items = listOf(
            OrderItem(product = sampleProducts[0], quantity = 1, isDelivered = false),
            OrderItem(product = sampleProducts[1], quantity = 2, isDelivered = false)
        ),
        status = 1,
        cancellationReason = null,
        customerId = "customer_123",
        note = null,
        createdAt = LocalDateTime.now()
    )

    ShoppingAppTheme {
        OrderDetailsScreen(
            navController = rememberNavController(),
            orderId = sampleOrder.id,
            orderState = OrderState(),
        )
    }
}