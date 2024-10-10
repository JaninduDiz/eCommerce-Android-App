package com.example.shoppingapp.views.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shoppingapp.models.Order
import com.example.shoppingapp.viewmodels.ProductState
import com.example.shoppingapp.views.orderStatusText
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderSummaryCard(
    order: Order,
    navController: NavController,
    productState: ProductState
) {
    val totalAmount = order.totalValue
    var firstProductName by remember { mutableStateOf<String?>(null) }
    val otherProductsCount = order.items.size - 1

    firstProductName = productState.products.find { it.productId == order.items.first().productId }?.name

    // Title showing first product + other product count
    val title = if (otherProductsCount > 0) {
        "$firstProductName + $otherProductsCount more"
    } else {
        firstProductName
    }

    // Card to display order information
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                // Navigate to the OrderDetails screen with the order ID
                navController.navigate("orderDetails/${order.id}/false")
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (title != null) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 2.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
            }
            Text(
                text = "Placed on ${formatDateTime(order.createdAt)}",
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 4.dp),
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
            Text(
                text = "Total: $${totalAmount + 2.0}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )
            val (statusText, statusColor) = orderStatusText(order.status)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Left
            ) {
                Text(text = "Status: ", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                Text(text = statusText, fontWeight = FontWeight.SemiBold, color = statusColor, fontSize = 12.sp)
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun formatDateTime(dateTimeString: String): String {
    // Define the input date-time format
    val inputFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME

    // Parse the input string to ZonedDateTime
    val zonedDateTime = ZonedDateTime.parse(dateTimeString, inputFormatter)

    // Define the desired output format
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd 'at' HH:mm")

    // Format the ZonedDateTime to the desired output format
    return zonedDateTime.format(outputFormatter)
}
