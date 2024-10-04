package com.example.shoppingapp.views

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.models.Order
import com.example.shoppingapp.models.User
import com.example.shoppingapp.models.sampleProducts
import com.example.shoppingapp.session.UserSessionManager
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.viewmodels.CartState
import com.example.shoppingapp.viewmodels.OrderState
import com.example.shoppingapp.views.components.CustomButton
import com.example.shoppingapp.views.components.CustomModal
import com.example.shoppingapp.views.components.CustomTopAppBar
import com.example.shoppingapp.views.components.ModalType
import java.time.LocalDateTime
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CheckoutScreen(navController: NavController,  cartState: CartState, totalPrice: Double) {
    var showModal by remember { mutableStateOf(false) }
    var apiResponseStatus by remember { mutableStateOf<ApiResponseStatus?>(null) }  // State for API response status
    var note by remember { mutableStateOf("") }

    val userSessionManager = UserSessionManager(LocalContext.current)
    val currentUser = userSessionManager.getUser()

    val orderState = OrderState()
    var order: Order = Order("", "", emptyList(), 0, null, null, LocalDateTime.now())

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
            OrderSummary(totalItems = cartState.items.size, totalPrice = totalPrice)

            Spacer(modifier = Modifier.height(16.dp))

            if (currentUser != null) {
                AddressSection(user = currentUser)
            }

            Spacer(modifier = Modifier.height(16.dp))

            PaymentMethodSection()

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Add note") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFf8f7ff),
                        unfocusedContainerColor = Color(0xFFf8f7ff),
                        focusedBorderColor = Color(0xFF9381ff),
                        unfocusedBorderColor = Color(0xFFf8f7ff),
                        cursorColor = Color(0xFF9381ff),
                    )
                )
            }

            // Confirm Order Button
            Spacer(modifier = Modifier.weight(1f))
            CustomButton(
                text = "Purchase Order",
                onClick = {
                    order = orderState.generateOrder(cartState, customerId = "customer_1123", note)         //TODO: Replace customerId with currentUser.id
                    val success = simulateApiCall()
                    apiResponseStatus =
                        if (success) ApiResponseStatus.SUCCESS else ApiResponseStatus.ERROR
                    showModal = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB98B73))
            )
        }

        // Modal logic
        if (showModal) {
            CustomModal(
                type = if (apiResponseStatus == ApiResponseStatus.SUCCESS) ModalType.SUCCESS else ModalType.ERROR,
                title = if (apiResponseStatus == ApiResponseStatus.SUCCESS) "Order placed successfully!" else "Order placement failed!",
                text = if (apiResponseStatus == ApiResponseStatus.SUCCESS) "Thank you for your order. You will receive a confirmation shortly." else "There was an issue placing your order. Please try again.",
                primaryButtonText = "OK",
                onPrimaryButtonClick = {
                    showModal = false
                    if (apiResponseStatus == ApiResponseStatus.SUCCESS) {
                        Log.d(TAG, "CheckoutScreen: Order placed successfully: $order")
                        navController.navigate("orderDetails/${order.id}/true")
                        cartState.clearCart()       //TODO: check this, this worked, not now
                    }
                },
                primaryButtonStyle = if (apiResponseStatus == ApiResponseStatus.SUCCESS) {
                    ButtonDefaults.buttonColors(containerColor = Color(0xFF6B705C))
                } else ButtonDefaults.buttonColors(containerColor = Color(0xFFc75146)),
                secondaryButtonText = null,
                tertiaryButtonText = null
            )
        }
    }
}

enum class ApiResponseStatus { SUCCESS, ERROR }
fun simulateApiCall(): Boolean {
    // Simulate an API call and return success or failure
    return true // or false based on the simulated response
}

@Composable
fun AddressSection(user: User) {
    Column {
        Text(
            text = "Delivery Address",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Address",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFa57f60))
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

@SuppressLint("DefaultLocale")
@Composable
fun OrderSummary(totalItems: Int, totalPrice: Double) {
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
            SummaryRow(label = "Subtotal", value = String.format(Locale.getDefault(),"$%.2f", totalPrice))
            Spacer(modifier = Modifier.height(10.dp))
            SummaryRow(label = "Delivery Charges", value = "$$deliveryCharge")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            SummaryRow(label = "Total", value = String.format(Locale.getDefault(), "$%.2f", totalPrice + deliveryCharge), isBold = true)
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
    var selectedPaymentMethod by remember { mutableStateOf("Cash") }

    Text(
        text = "Choose payment method",
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 16.dp)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { selectedPaymentMethod = "Cash" }
            .padding(bottom = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Cash on Delivery",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFFc4a381))
                .padding(12.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "Cash", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))

        if (selectedPaymentMethod == "Cash") {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color(0xFF3F4238),
                modifier = Modifier.size(30.dp)
            )
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { selectedPaymentMethod = "Add New" }
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add payment method",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFFc4a381))
                .padding(12.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "Add new payment method", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))

        if (selectedPaymentMethod == "Add New") {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color(0xFF3F4238),
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview() {
    ShoppingAppTheme {
        CheckoutScreen(
            navController = rememberNavController(),
            cartState = CartState(),
            totalPrice = (sampleProducts[0].price * 1 + sampleProducts[1].price * 2)
        )
    }
}
