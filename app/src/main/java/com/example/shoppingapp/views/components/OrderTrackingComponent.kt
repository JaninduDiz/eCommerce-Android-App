package com.example.shoppingapp.views.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.viewmodels.OrderState
import com.example.shoppingapp.viewmodels.ProductState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderTrackingComponent(navController: NavController, orderState: OrderState, productState: ProductState) {
    val orders = orderState.orders.filter { it.status == 0 || it.status == 1 || it.status == 2 }

    for (order in orders) {
        OrderSummaryCard(order = order, navController = navController, productState = productState)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun OrderTrackingComponentPreview() {
    ShoppingAppTheme {
        OrderTrackingComponent(
            navController = rememberNavController(), orderState = OrderState(), productState = ProductState()
        )
    }
}