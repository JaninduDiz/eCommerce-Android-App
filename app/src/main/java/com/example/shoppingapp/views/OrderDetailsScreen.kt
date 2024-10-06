package com.example.shoppingapp.views

import CustomModalBottomSheet
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shoppingapp.R
import com.example.shoppingapp.models.OrderItem
import com.example.shoppingapp.viewmodels.OrderState
import com.example.shoppingapp.views.components.CustomButton
import com.example.shoppingapp.views.components.CustomModal
import com.example.shoppingapp.views.components.CustomTopAppBar
import com.example.shoppingapp.views.components.ModalType
import com.example.shoppingapp.views.components.formatDateTime


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderDetailsScreen(
    navController: NavController,
    orderId: String,
    orderState: OrderState,
    isBackHome: Boolean? = false
) {
    val order = orderState.orders.find { it.id == orderId } ?: return
    var showModal by remember { mutableStateOf(false) }
    var showDrawer by remember { mutableStateOf(false) }
    var cancellationReason by remember { mutableStateOf("") }

    CustomTopAppBar(
        title = "Order Details",
        onNavigationClick = {
            if (isBackHome == true) navController.navigate("home")
            else navController.popBackStack()
        },
        centeredHeader = true,
        isHeaderPinned = true
    ) { paddingValues ->
        Scaffold(
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 28.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Cancel Order Button
                   if (order.status == 1 || order.status == 0 || order.status == 2) {
                       CustomButton(
                           text = "Cancel Order",
                           onClick = { showDrawer = true },
                           modifier = Modifier.fillMaxWidth(),
                           colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFc75146))
                       )
                    }
                }
            }
        ) { contentPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    // Order status and date placed
                    Text(text = "Order ID: ${order.id}", fontWeight = FontWeight.Bold)
                    Text(
                        text = "Date Placed: ${formatDateTime(order.createdAt)}",
                        fontWeight = FontWeight.Bold
                    )
                    val (statusText, statusColor) = orderStatusText(order.status)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(text = "Status: ", fontWeight = FontWeight.Bold)
                        Text(
                            text = statusText,
                            fontWeight = FontWeight.Bold,
                            color = statusColor
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(order.items) { orderItem ->
                    OrderItemCard(orderItem, navController)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    OrderSummary(
                        totalItems = order.items.size,
                        totalPrice = order.totalValue,
                        deliveryCharge = 2.0
                    )

                    Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))

                }

                item {
                    if (showModal) {
                        CustomModal(
                            type = ModalType.ERROR,
                            title = "Cancel Order?",
                            text = "Are you sure you want to cancel this order?",
                            primaryButtonText = "Confirm",
                            onPrimaryButtonClick = {
                                showModal = false
                                showDrawer = false
                                // TODO: Implement cancel order logic
                            },
                            primaryButtonStyle = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFc75146)
                            ),
                            secondaryButtonText = "Close",
                            onSecondaryButtonClick = {
                                showModal = false
                                showDrawer = false
                            },
                        )
                    }

                    if (showDrawer) {
                        CustomModalBottomSheet(
                            onDismiss = { showDrawer = false },
                            sheetContent = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(),

                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Please provide a reason for cancelling\nthe order",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                        modifier = Modifier.padding(bottom = 8.dp),
                                        textAlign = TextAlign.Center
                                    )

                                    OutlinedTextField(
                                        value = cancellationReason,
                                        onValueChange = {
                                            cancellationReason = it
                                        },
                                        label = { Text("Cancellation Reason") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedContainerColor = Color(0xFFf8f7ff),
                                            unfocusedContainerColor = Color(0xFFf8f7ff),
                                            focusedBorderColor = Color(0xFF9381ff),
                                            unfocusedBorderColor = Color.Gray,
                                            cursorColor = Color(0xFF9381ff)
                                        ),
                                        maxLines = 4
                                    )
                                }
                            },
                            buttonText = "Cancel Order",
                            onButtonClick = {
                                showModal = true
                            }
                        )
                    }
                }
            }
        }
    }
}


//get the order status text and color
@Composable
fun orderStatusText(status: Int): Pair<String, Color> {
    return when (status) {
        0 -> "Processing" to Color(0xFF673AB7) // Green
        1 -> "Ready For Delivery" to Color(0xFF009688) // Amber
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
                navController.navigate("reviewScreen/${orderItem.productId}")
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
//                Text(text = orderItem.product.name, fontWeight = FontWeight.Bold)
                Text(text = "Quantity: ${orderItem.quantity}")
                Text(text = "Price: $${orderItem.unitPrice}")
            }
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@SuppressLint("UnrememberedMutableState")
//@Preview(showBackground = true)
//@Composable
//fun OrderDetailsScreenPreview() {
//    val sampleOrder = Order(
//        id = "order_1",
//        items = listOf(
//            OrderItem(product = sampleProducts[0], quantity = 1, isDelivered = false),
//            OrderItem(product = sampleProducts[1], quantity = 2, isDelivered = false)
//        ),
//        status = 1,
//        cancellationReason = null,
//        customerId = "customer_123",
//        note = null,
//        createdAt = LocalDateTime.now()
//    )
//
//    ShoppingAppTheme {
//        OrderDetailsScreen(
//            navController = rememberNavController(),
//            orderId = sampleOrder.id,
//            orderState = OrderState(),
//        )
//    }
//}