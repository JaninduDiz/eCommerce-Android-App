package com.example.shoppingapp.views.tabViews

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
fun SearchScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Search", fontSize = 24.sp)
        // Add your Search screen content here
    }
}


@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    ShoppingAppTheme {
        SearchScreen(navController = rememberNavController())
    }
}