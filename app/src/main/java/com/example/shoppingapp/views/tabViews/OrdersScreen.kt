package com.example.shoppingapp.views.tabViews

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.utils.RetrofitInstance
import com.example.shoppingapp.utils.UserSessionManager
import com.example.shoppingapp.viewmodels.OrderState
import com.example.shoppingapp.views.components.OrderHistoryComponent
import com.example.shoppingapp.views.components.OrderTrackingComponent
import com.example.shoppingapp.views.components.ShimmerComponents.OrderShimmeringPlaceholder

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrdersScreen(navController: NavController, orderState: OrderState, paddingValues: PaddingValues) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var loading by remember { mutableStateOf(false) }
    val userSessionManager = UserSessionManager(LocalContext.current)
    val currentUser = userSessionManager.getUser()

    //fetching orders
    LaunchedEffect(Unit) {
        try {
            loading = true
            val response = RetrofitInstance.api.getOrdersByCustomerId(currentUser.id)
            if (response.isSuccessful) {
                val orders = response.body()
                if (orders != null) {
                    orderState.addOrder(orders)
                }
                loading = false
            } else {
                loading = false
                Log.d(TAG, "OrderScreen: no orders found")
            }
        } catch (e: Exception) {
            loading = false
            Log.d(TAG, "OrderScreen: ${e.message}, error fetching orders")
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .offset(y = (-16).dp)
            .padding(paddingValues)
    ) {
        item {
            Text(
                text = "Orders",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        item {
            OrderSelector(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (!loading) {
            item {
                when (selectedTab) {
                    0 -> OrderTrackingComponent(navController, orderState)
                    1 -> OrderHistoryComponent(navController, orderState)
                }
            }
        } else {
            items(5) {
                OrderShimmeringPlaceholder()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderSelector(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val activeButtonColor = Color(0xFF669bbc)
    val inactiveButtonColor = Color.White
    val activeTextColor = Color.White
    val inactiveTextColor = Color(0xFF3F4238)

    val tabs = listOf("Order Tracking", "Order History") // List of tab names

    // TabRow to manage the selected tab
    TabRow(
        selectedTabIndex = selectedTab,
        contentColor = activeButtonColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(50.dp)
            .background(color = Color.White)
            .shadow(8.dp, shape = RoundedCornerShape(50.dp)),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                height = 4.dp,
                color = activeButtonColor
            )
        },
        divider = {} // Remove default bottom divider
    ) {
        // Loop through the tabs
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                modifier = Modifier
                    .background(
                        color = if (selectedTab == index) activeButtonColor else inactiveButtonColor,
                        shape = RoundedCornerShape(50.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(50.dp),
                text = {
                    Text(
                        text = title,
                        color = if (selectedTab == index) activeTextColor else inactiveTextColor
                    )
                }
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun OrdersScreenPreview() {
    ShoppingAppTheme {
        OrdersScreen(navController = rememberNavController(), orderState = OrderState(), paddingValues = PaddingValues(0.dp))
    }
}