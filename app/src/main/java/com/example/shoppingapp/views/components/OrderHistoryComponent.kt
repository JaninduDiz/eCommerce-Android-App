package com.example.shoppingapp.views.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.viewmodels.OrderState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderHistoryComponent(navController: NavController, orderState: OrderState) {
    val orders = orderState.orders.filter { it.status == 3 || it.status == 4 }

    for (order in orders) {
        OrderSummaryCard(order = order, navController = navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun OrderHistoryComponentPreview() {
    ShoppingAppTheme {
        OrderHistoryComponent(
            navController = rememberNavController(), orderState = OrderState()
        )
    }
}