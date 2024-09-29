package com.example.shoppingapp.views

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shoppingapp.models.Order
import com.example.shoppingapp.models.User
import com.example.shoppingapp.models.sampleUser
import com.example.shoppingapp.views.components.CustomButton
import com.example.shoppingapp.views.components.CustomTopAppBar

@Composable
fun CheckoutScreen(navController: NavController, order: Order, totalPrice: Int) {
    CustomTopAppBar(
        title = "Checkout",
        onNavigationClick = { navController.popBackStack() },
        centeredHeader = true
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            OrderSummary(totalItems = order.items.size, totalPrice = totalPrice)

            Spacer(modifier = Modifier.height(16.dp))

            AddressSection(user = sampleUser)

            PaymentMethodSection()

            // Confirm Order Button
            Spacer(modifier = Modifier.weight(1f))
            CustomButton(
                text = "Confirm Order",
                onClick = { /* Handle confirm order */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB98B73))
            )
        }
    }
}

@Composable
fun AddressSection(user: User) {
    Column { // Address Section
        Text(
            text = "Delivery Address",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Address",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFB98B73))
                        .padding(12.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = user.addressLine1 + ", " + user.addressLine2,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(text = user.city + ", " + user.postalCode, fontSize = 12.sp, color = Color.Gray)
                }
            }
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Selected",
                tint = Color(0xFF3F4238)
            )
        }
    }
}

@Composable
fun OrderSummary(totalItems: Int, totalPrice: Int) {
    val deliveryCharge = 2
    Text(
        text = "Order Summary",
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F1F1)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            SummaryRow(label = "Total Items", value = "$totalItems")
            SummaryRow(label = "Subtotal", value = "$$totalPrice")
            Spacer(modifier = Modifier.height(10.dp))
            SummaryRow(label = "Delivery Charges", value = "$$deliveryCharge")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            SummaryRow(label = "Total", value = "$${totalPrice + deliveryCharge}", isBold = true)
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 14.sp, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal)
        Text(text = value, fontSize = 14.sp, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal)
    }
}

@Composable
fun PaymentMethodSection() {
    Text(
        text = "Choose payment method",
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 16.dp)
    )

    // Cash Payment Method
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Cash",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFFB98B73))
                .padding(12.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "Cash", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Selected",
            tint = Color(0xFF3F4238),
            modifier = Modifier.size(30.dp)
        )
    }

    // Add New Payment Method
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle add new payment method */ }
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add payment method",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFFB98B73))
                .padding(12.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "Add new payment method", fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun CheckoutScreenPreview() {
//    // Sample products for order
//    val sampleProduct1 = Product(
//        productId = "prod1",
//        name = "Chair",
//        category = Category("cat1", "Furniture"),
//        vendorId = "vendor1",
//        isActive = true,
//        price = 150.0,
//        description = "Comfortable office chair",
//        stock = 10
//    )
//
//    val sampleProduct2 = Product(
//        productId = "prod2",
//        name = "Desk",
//        category = Category("cat2", "Furniture"),
//        vendorId = "vendor2",
//        isActive = true,
//        price = 200.0,
//        description = "Spacious office desk",
//        stock = 5
//    )
//
//    // Sample order items
//    val sampleOrderItems = listOf(
//        OrderItem(product = sampleProduct1, quantity = , isDelivered = false),
//        OrderItem(product = sampleProduct2, quantity = 1, isDelivered = false)
//    )
//
//    // Sample order
//    val sampleOrder = Order(
//        id = "order1",
//        customerId = "customer1",
//        items = sampleOrderItems,
//        status = 1,
//        cancellationReason = null,
//        note = "Please deliver carefully."
//    )
//
//    // Mock NavController for preview
//    val navController = rememberNavController()
//
//    // Render CheckoutScreen with mock data
//    ShoppingAppTheme {
//        CheckoutScreen(navController = navController, order = sampleOrder, totalPrice = 500)
//    }
//}