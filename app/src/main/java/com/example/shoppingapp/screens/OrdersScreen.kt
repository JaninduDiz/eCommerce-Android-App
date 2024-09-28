package com.example.shoppingapp.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.ui.theme.ShoppingAppTheme

@Composable
fun OrdersScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Orders", fontSize = 24.sp)
        // Add your Orders screen content here
    }
}

@Preview(showBackground = true)
@Composable
fun OrdersScreenPreview() {
    ShoppingAppTheme {
        OrdersScreen(navController = rememberNavController())
    }
}