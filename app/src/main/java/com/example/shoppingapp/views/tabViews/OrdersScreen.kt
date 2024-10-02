package com.example.shoppingapp.views.tabViews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.views.components.OrderHistoryComponent
import com.example.shoppingapp.views.components.OrderTrackingComponent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrdersScreen(navController: NavController) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Orders",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OrderSelector(selectedTab = selectedTab, onTabSelected = { selectedTab = it })

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTab) {
            0 -> OrderTrackingComponent(navController)
            1 -> OrderHistoryComponent(navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderSelector(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val activeButtonColor = Color(0xFFB98B73)  // Active button background color
    val inactiveButtonColor = Color.White
    val activeTextColor = Color.White
    val inactiveTextColor = Color(0xFF3F4238)  // Darker text color for inactive

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(50.dp)
            .shadow(8.dp, shape = RoundedCornerShape(50.dp))
            .background(Color.White, shape = RoundedCornerShape(50.dp))
            .border(0.1.dp, Color.White, shape = RoundedCornerShape(50.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Order Tracking Button
        Button(
            onClick = { onTabSelected(0) }, // Update the selectedTab when clicked
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedTab == 0) activeButtonColor else inactiveButtonColor
            ),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
        ) {
            Text(
                text = "Order Tracking",
                color = if (selectedTab == 0) activeTextColor else inactiveTextColor
            )
        }

        // Order History Button
        Button(
            onClick = { onTabSelected(1) }, // Update the selectedTab when clicked
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedTab == 1) activeButtonColor else inactiveButtonColor
            ),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
        ) {
            Text(
                text = "Order History",
                color = if (selectedTab == 1) activeTextColor else inactiveTextColor
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun OrdersScreenPreview() {
    ShoppingAppTheme {
        OrdersScreen(navController = rememberNavController())
    }
}