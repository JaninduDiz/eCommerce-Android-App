package com.example.shoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import androidx.compose.ui.tooling.preview.Preview
import com.example.shoppingapp.helpers.CartState
import com.example.shoppingapp.onBoard.LoginScreen
import com.example.shoppingapp.onBoard.RegisterScreen
import com.example.shoppingapp.screens.CartScreen
import com.example.shoppingapp.screens.CategoryScreen
import com.example.shoppingapp.screens.HomeScreen
import com.example.shoppingapp.screens.ProductDetailsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingAppTheme {
                val navController = rememberNavController()
                val cartState = remember { CartState() }

                NavHost(navController, startDestination = "home") {
//                    composable("login") { LoginScreen(navController) }
//                    composable("register") { RegisterScreen(navController) }
                    composable("home") { HomeScreen(navController, cartState) }
                    composable("productDetails/{productId}") { backStackEntry ->
                        val productId = backStackEntry.arguments?.getString("productId")
                        ProductDetailsScreen(navController, productId, cartState)
                    }
                    composable("categoryScreen/{categoryId}") { backStackEntry ->
                        val categoryId = backStackEntry.arguments?.getString("categoryId")
                        CategoryScreen(
                            navController = navController,
                            categoryId = categoryId
                        )
                    }
                    composable("cart") { CartScreen(navController, cartState) }
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    ShoppingAppTheme {
        val navController = rememberNavController()
        HomeScreen(navController, CartState())
    }
}




