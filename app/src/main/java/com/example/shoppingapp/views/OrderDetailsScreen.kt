package com.example.shoppingapp.views

import CustomModalBottomSheet
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.models.OrderItem
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.utils.RetrofitInstance
import com.example.shoppingapp.viewmodels.OrderState
import com.example.shoppingapp.viewmodels.ProductState
import com.example.shoppingapp.views.components.CustomButton
import com.example.shoppingapp.views.components.CustomModal
import com.example.shoppingapp.views.components.CustomTopAppBar
import com.example.shoppingapp.views.components.ModalType
import com.example.shoppingapp.views.components.formatDateTime
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderDetailsScreen(
    navController: NavController,
    orderId: String,
    orderState: OrderState,
    productState: ProductState,
    isBackHome: Boolean? = false
) {
    val context = LocalContext.current
    val order = orderState.orders.find { it.id == orderId } ?: return
    var showModal by remember { mutableStateOf(false) }
    var showDrawer by remember { mutableStateOf(false) }
    var cancellationReason by remember { mutableStateOf("") }
    val getProductDetails: (String) -> Product? = { productId -> productState.products.find { it.productId == productId } }

    val getProductDetailsToUpdateOrder: (List<OrderItem>, ProductState) -> List<OrderItem> = { orderItems, productStated ->
        orderItems.map { orderItem ->
            val product = productStated.products.find { it.productId == orderItem.productId }
            if (product != null) {
                orderItem.copy(unitPrice = product.price)
            } else {
                orderItem
            }
        }
    }

    val textFieldValue = if ((order.status in listOf(0, 1, 2, 3) && !order.note.isNullOrEmpty()) || (order.status in listOf(4, 5) && !order.cancellationReason.isNullOrEmpty())) {
        order.note ?: order.cancellationReason
    } else {
        ""
    }

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

                    if (order.status == 3) {
                        CustomButton(
                            text = "Order Delivered",
                            onClick = { },
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3d5a80))
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
                    .padding(top = 16.dp)
                    .offset(y = (-24).dp)
            ) {
                item {
                    // Order status and date placed
                    Text(text = "Order ID: ${order.id}", fontWeight = FontWeight.SemiBold, fontSize = 16.sp )
                    Text(
                        text = "Date Placed: ${formatDateTime(order.createdAt)}",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                    val (statusText, statusColor) = orderStatusText(order.status)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(text = "Status: ", fontWeight = FontWeight.Medium, fontSize = 14.sp )
                        Text(
                            text = statusText,
                            fontWeight = FontWeight.Medium,
                            color = statusColor,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(order.items) { orderItem ->
                    OrderItemCard(orderItem, navController, getProductDetails, order.status)
                }

                if (textFieldValue != "") {
                    item {
                        if (textFieldValue != null) {
                            OutlinedTextField(
                                value = textFieldValue,
                                onValueChange = { },
                                label = { Text("Your note") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color(0xFFf8f7ff),
                                    unfocusedContainerColor = Color(0xFFf8f7ff),
                                    focusedBorderColor = Color(0xFF9381ff),
                                    unfocusedBorderColor = Color(0xFFf8f7ff),
                                    cursorColor = Color(0xFF9381ff),
                                ),
                                enabled = false
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))

                    OrderSummary(
                        totalItems = order.items.size,
                        totalPrice = order.totalValue,
                        deliveryCharge = 2.0
                    )

                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp))
                }

                item {
                    if (showModal) {
                        CustomModal(
                            type = ModalType.ERROR,
                            title = "Cancel Order?",
                            text = "Are you sure you want to cancel this order?",
                            primaryButtonText = "Confirm",
                            onPrimaryButtonClick = {
                                (context as ComponentActivity).lifecycleScope.launch {
                                    try {
                                        val updatedOrderItems = getProductDetailsToUpdateOrder(order.items, productState)
                                        val updateOrder = orderState.cancelOrder(order.id, updatedOrderItems, cancellationReason, order.totalValue)

                                        val response = updateOrder?.let {
                                            Log.d(TAG, "OrderDetailsScreen: updatedOrder: $updateOrder")
                                            RetrofitInstance.api.updateOrder(order.id, updateOrder)
                                        }
                                        Log.d(TAG, "OrderDetailsScreen: response: $response")
                                        if (response != null) {
                                            if (response.isSuccessful) {
                                                Log.d(TAG, "OrderDetailsScreen: response: ${response.body()}")
                                            } else {
                                                Toast.makeText(context, "Update failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                                                Log.d(TAG, "OrderDetailsScreen: Update failed: ${response.errorBody()?.string()}")
                                            }
                                        }
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                                        Log.d(TAG, "Update: Error: ${e.localizedMessage}")
                                    }
                                }
                                showModal = false
                                showDrawer = false
                            },
                            primaryButtonStyle = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFf07167)
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
        0 -> "Processing" to Color(0xFF673AB7)
        1 -> "Ready For Delivery" to Color(0xFF009688)
        2 -> "Partially Delivered" to Color(0xFF2196F3)
        3 -> "Delivered" to Color(0xFF8BC34A)
        4 -> "Cancelled" to Color(0xFFF44336)
        5 -> "Requested to Cancel" to Color(0xFFF44336)
        else -> "Unknown" to Color(0xFF9E9E9E)
    }
}

@Composable
fun OrderItemCard(orderItem: OrderItem, navController: NavController, getProductDetails: (String) -> Product?, orderStatus: Int) {
    val product = getProductDetails(orderItem.productId) ?: return
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                if (orderStatus == 3) {
                    navController.navigate("reviewScreen/${orderItem.productId}/${orderItem.vendorId}")
                } else {
                    navController.navigate("productDetails/${orderItem.productId}")
                }
            },
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.imageUrls.first()),
                contentDescription = "Product Image",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = product.name, fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = "Quantity: ${orderItem.quantity}", fontSize = 12.sp)
                Text(text = "Price: $${product.price}", fontSize = 12.sp)
            }
        }
    }
}
