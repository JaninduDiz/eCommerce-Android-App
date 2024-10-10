package com.example.shoppingapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.services.gettoken
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.utils.UserSessionManager
import com.example.shoppingapp.viewmodels.CartState
import com.example.shoppingapp.viewmodels.CategoryState
import com.example.shoppingapp.viewmodels.OrderState
import com.example.shoppingapp.viewmodels.ProductState
import com.example.shoppingapp.viewmodels.VendorState
import com.example.shoppingapp.views.*
import com.example.shoppingapp.views.onBoardViews.LoginScreen
import com.example.shoppingapp.views.onBoardViews.RegisterScreen
import com.example.shoppingapp.views.tabViews.HomeScreen

class MainActivity : ComponentActivity() {
    private val POST_NOTIFICATIONS_PERMISSION_REQUEST_CODE = 101

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Check for POST_NOTIFICATIONS permission on Android 13 (API 33) and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkAndRequestNotificationPermission()
        }

        val token = gettoken()



        setContent {
            ShoppingAppTheme {

                // passing the session manager, navigation controller and states
                val navController = rememberNavController()
                val cartState = remember { CartState() }
                val orderState = remember { OrderState() }
                val productState = remember { ProductState() }
                val vendorState = remember { VendorState() }
                val categoryState = remember { CategoryState() }
                val userSessionManager = UserSessionManager(this)
                val currentUser = userSessionManager.getUser()

                NavHost(
                    navController,
                    startDestination = if (currentUser.id != "") "home" else "login"
                ) {

                    composable("login") {
                        LoginScreen(navController, userSessionManager)
                    }
                    composable("register") { RegisterScreen(navController) }
                    composable("home") {
                        HomeScreen(
                            navController,
                            cartState,
                            orderState,
                            productState,
                            categoryState
                        )
                    }
                    composable("productDetails/{productId}") { backStackEntry ->
                        val productId = backStackEntry.arguments?.getString("productId")
                        if (productId != null) {
                            ProductDetailsScreen(
                                navController,
                                productId,
                                cartState,
                                vendorState,
                                categoryState
                            )
                        }
                    }
                    composable("reviewScreen/{productId}/{vendorId}") { backStackEntry ->
                        val productId = backStackEntry.arguments?.getString("productId")
                        val vendorId = backStackEntry.arguments?.getString("vendorId")
                        ReviewScreen(
                            navController,
                            productId,
                            vendorId,
                            productState,
                            categoryState
                        )
                    }
                    composable("categoryScreen/{categoryId}") { backStackEntry ->
                        val categoryId = backStackEntry.arguments?.getString("categoryId")
                        CategoryScreen(
                            navController = navController,
                            categoryId = categoryId,
                            categoryState = categoryState,
                            productState = productState
                        )
                    }
                    composable("checkoutScreen/{totalPrice}") { backStackEntry ->
                        val totalPrice = backStackEntry.arguments?.getString("totalPrice")
                            ?.toDoubleOrNull() ?: 0.0
                        CheckoutScreen(navController = navController, cartState, totalPrice = totalPrice)
                    }
                    composable("profile") { ProfileScreen(navController, userSessionManager) }
                    composable("orderDetails/{orderId}/{isBackHome}") { backStackEntry ->
                        val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
                        val isBackHome = backStackEntry.arguments?.getString("isBackHome")?.toBoolean() ?: false
                        OrderDetailsScreen(navController, orderId, orderState, productState, isBackHome)
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

    // Method to check and request notification permission for Android 13+
    private fun checkAndRequestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the POST_NOTIFICATIONS permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                POST_NOTIFICATIONS_PERMISSION_REQUEST_CODE
            )
        }
    }

    // Handle the result of the permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == POST_NOTIFICATIONS_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, notifications can now be sent
            } else {
                // Permission denied, handle the case
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




