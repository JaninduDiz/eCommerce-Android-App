package com.example.shoppingapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.utils.UserSessionManager
import com.example.shoppingapp.viewmodels.CartState
import com.example.shoppingapp.viewmodels.CategoryState
import com.example.shoppingapp.viewmodels.OrderState
import com.example.shoppingapp.viewmodels.ProductState
import com.example.shoppingapp.viewmodels.VendorState
import com.example.shoppingapp.views.CategoryScreen
import com.example.shoppingapp.views.CheckoutScreen
import com.example.shoppingapp.views.OrderDetailsScreen
import com.example.shoppingapp.views.ProductDetailsScreen
import com.example.shoppingapp.views.ProfileScreen
import com.example.shoppingapp.views.ReviewScreen
import com.example.shoppingapp.views.VendorScreen
import com.example.shoppingapp.views.onBoardViews.LoginScreen
import com.example.shoppingapp.views.onBoardViews.RegisterScreen
import com.example.shoppingapp.views.tabViews.CartScreen
import com.example.shoppingapp.views.tabViews.HomeScreen


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            ShoppingAppTheme {
                val navController = rememberNavController()
                val cartState = remember { CartState() }
                val orderState = remember { OrderState() }
                val productState = remember { ProductState() }
                val vendorState = remember { VendorState() }
                val categoryState = remember { CategoryState() }
                val userSessionManager = UserSessionManager(this)
                val currentUser = userSessionManager.getUser()

                NavHost(navController, startDestination = if (currentUser != null) "home" else "login") {

                    composable("login") {
                        LoginScreen(navController, userSessionManager) // pass the session manager
                    }
                    composable("register") { RegisterScreen(navController) }
                    composable("home") { HomeScreen(navController, cartState, orderState, productState, categoryState ) }
                    composable("productDetails/{productId}") { backStackEntry ->
                        val productId = backStackEntry.arguments?.getString("productId")
                        if (productId != null) {
                            ProductDetailsScreen(navController, productId, cartState, vendorState)
                        }
                    }
                    composable("reviewScreen/{productId}/{vendorId}") { backStackEntry ->
                        val productId = backStackEntry.arguments?.getString("productId")
                        val vendorId = backStackEntry.arguments?.getString("vendorId")
                        ReviewScreen(navController, productId, vendorId, productState)
                    }
                    composable("categoryScreen/{categoryId}") { backStackEntry ->
                        val categoryId = backStackEntry.arguments?.getString("categoryId")
                        CategoryScreen(
                            navController = navController,
                            categoryId = categoryId,
                            categoryState = categoryState
                        )
                    }
                    composable("cart") { CartScreen(navController, cartState) }
                    composable("checkoutScreen/{totalPrice}") { backStackEntry ->
                        val totalPrice = backStackEntry.arguments?.getString("totalPrice")?.toDoubleOrNull() ?: 0.0
                        CheckoutScreen(navController = navController, cartState, totalPrice = totalPrice)
                    }
                    composable("profile") { ProfileScreen(navController , userSessionManager) }
                    composable("orderDetails/{orderId}/{isBackHome}") { backStackEntry ->
                        val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
                        val isBackHome = backStackEntry.arguments?.getString("isBackHome")?.toBoolean() ?: false

                        OrderDetailsScreen(navController, orderId, orderState, isBackHome)
                    }
                    composable("vendorDetails/{vendorId}") { backStackEntry ->
                        val vendorId = backStackEntry.arguments?.getString("vendorId")
                        if (vendorId != null) {
                            VendorScreen(navController, vendorId, vendorState)
                        }
                    }
                }
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun DefaultPreview() {
    ShoppingAppTheme {
        val navController = rememberNavController()
        HomeScreen(navController, CartState(), OrderState(), ProductState(), CategoryState())
    }
}




