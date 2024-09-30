package com.example.shoppingapp.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.shoppingapp.models.sampleProducts
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.viewmodels.OrderState
import com.example.shoppingapp.views.components.CustomButton
import com.example.shoppingapp.views.components.CustomModal
import com.example.shoppingapp.views.components.CustomTopAppBar
import com.example.shoppingapp.views.components.ModalType

@SuppressLint("UnrememberedMutableState")
@Composable
fun OrderDetailsScreen(navController: NavController, orderId: String, orderState: OrderState, isBackHome: Boolean? = false) {
    val order = orderState.getOrderById(orderId)
    var showModal by remember { mutableStateOf(false) }  // State for showing modal

    CustomTopAppBar(
        title = "Order Details",
        onNavigationClick = {
            if (isBackHome == true) navController.navigate("home")
            else navController.popBackStack()
        },
        centeredHeader = true
    ) { paddingValues ->
        order?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Order status and date placed
                Text(text = "Order ID: ${it.id}", fontWeight = FontWeight.Bold)
                Text(text = "Status: ${it.status}", fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                // List the items in the order
                it.items.forEach { orderItem ->
                    OrderItemCard(orderItem)
                }

                Spacer(modifier = Modifier.height(16.dp))

                OrderSummary(
                    totalItems = it.items.size,
                    totalPrice = it.items.sumOf { item -> item.product.price * item.quantity.value }
                )

                Spacer(modifier = Modifier.weight(1f))

                // Cancel Order Button
                CustomButton(
                    text = "Cancel Order",
                    onClick = {  showModal = true },
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
        } ?: run {
            Text(text = "Order not found.", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun OrderItemCard(orderItem: OrderItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun OrderDetailsScreenPreview() {
    val sampleOrder = Order(
        id = "order_1",
        customerId = "customer_1",
        items = listOf(
            OrderItem(product = sampleProducts[0], quantity = mutableIntStateOf(1), isDelivered = false),
            OrderItem(product = sampleProducts[1], quantity = mutableIntStateOf(2), isDelivered = false)
        ),
        status = 1,  // Assuming status "1" means "Pending"
        cancellationReason = null,
        note = null
    )

    ShoppingAppTheme {
        OrderDetailsScreen(
            navController = rememberNavController(),
            orderId = sampleOrder.id,
            orderState = OrderState(),
        )
    }
}