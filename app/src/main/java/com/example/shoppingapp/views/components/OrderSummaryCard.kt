package com.example.shoppingapp.views.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.shoppingapp.models.Order

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderSummaryCard(
    order: Order,
    navController: NavController,
) {
//    val context = LocalContext.current
//
//    // Format the date in "YYYY, MMM-DD at HH:mm"
//    val formattedDate = order.createdAt.format(DateTimeFormatter.ofPattern("yyyy, MMM-dd 'at' HH:mm"))
//
//    // Calculate total price
//    val totalAmount = order.items.sumOf { it.product.price * it.quantity }
//
//    // Extract the first product name
//    val firstProductName = order.items.first().product.name
//    val otherProductsCount = order.items.size - 1
//
//    // Title showing first product + other product count
//    val title = if (otherProductsCount > 0) {
//        "$firstProductName + $otherProductsCount more"
//    } else {
//        firstProductName
//    }
//
//    // Card to display order information
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//            .clickable {
//                // Navigate to the OrderDetails screen with the order ID
//                navController.navigate("orderDetails/${order.id}/false")
//            },
//       // elevation = 4.dp,
//        shape = RoundedCornerShape(8.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(
//                text = title,
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp,
//                modifier = Modifier.padding(bottom = 4.dp)
//            )
//            Text(
//                text = "Placed on $formattedDate",
//                fontSize = 14.sp,
//                color = Color.Gray,
//                modifier = Modifier.padding(bottom = 4.dp)
//            )
//
//            val (statusText, statusColor) = orderStatusText(order.status)
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.Absolute.Left
//            ) {
//                Text(text = "Status: ", fontWeight = FontWeight.Bold)
//                Text(text = statusText, fontWeight = FontWeight.Bold, color = statusColor)
//            }
//            Text(
//                text = "Total: $${"%.2f".format(totalAmount)}",
//                fontWeight = FontWeight.Bold,
//                fontSize = 16.sp
//            )
//        }
//    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun OrderSummaryCardPreview() {
//    val sampleOrder = sampleOrders.first()
//    val navController = rememberNavController()
//
//    OrderSummaryCard(
//        order = sampleOrder,
//        navController = navController,
//    )
}