package com.example.shoppingapp.views.tabViews

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.shoppingapp.views.components.CustomButton
import com.example.shoppingapp.views.components.CustomTopAppBar

@Composable
fun OrderDetailsScreen(navController: NavController, order: Order) {
    CustomTopAppBar(
        title = "Order Details",
        onNavigationClick = { navController.popBackStack() },
        centeredHeader = true
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Order status and date placed
            Text(text = "Order ID: ${order.id}", fontWeight = FontWeight.Bold)
            Text(text = "Status: ${order.status}", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            // List the items in the order
            order.items.forEach { orderItem ->
                OrderItemCard(orderItem)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Total price at the bottom
            Text(text = "Total: $${"%.2f".format(order.items.sumOf { it.product.price * it.quantity.value })}")

            Spacer(modifier = Modifier.weight(1f))

            // Cancel Order Button
            CustomButton(
                text = "Cancel Order",
                onClick = { /* Handle order cancellation */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFc75146))
            )
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
            order = sampleOrder
        )
    }
}