package com.example.shoppingapp.views.tabViews

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.models.Category
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.utils.RetrofitInstance
import com.example.shoppingapp.viewmodels.CartState
import com.example.shoppingapp.viewmodels.CategoryState
import com.example.shoppingapp.viewmodels.OrderState
import com.example.shoppingapp.viewmodels.ProductState
import com.example.shoppingapp.views.components.CircularIndicator

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, cartState: CartState, orderState: OrderState ,productState: ProductState , categoryState: CategoryState) {
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Orders",
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Cart",
            selectedIcon = Icons.Filled.ShoppingCart,
            unselectedIcon = Icons.Outlined.ShoppingCart,
            hasNews = cartState.items.isNotEmpty(),
            badgeCount = if (cartState.items.isNotEmpty()) cartState.items.size else null
        ),
        BottomNavigationItem(
            title = "Search",
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
            hasNews = false,
        )
    )

    var loading by remember { mutableStateOf(false) }

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }

    // Fetch categories
    LaunchedEffect(Unit) {
        try {
            loading = true
            val response = RetrofitInstance.api.getCategories()
            if (response.isSuccessful) {
                categories = response.body() ?: emptyList()
                categoryState.clear()
                categoryState.addCategories(categories)
                loading = false
            }
        } catch (e: Exception) {
            loading = false
            Log.d(TAG, "HomeScreen: ${e.message}, error fetching categories")
        }
    }

    // Fetch products
//    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
//    LaunchedEffect(Unit) {
//        try {
//            val response = RetrofitInstance.api.getActiveProducts()
//            if (response.isSuccessful) {
//                products = response.body() ?: emptyList()
//                productState.addProducts(products)
//            }
//        } catch (e: Exception) {
//            // Handle error (e.g., show a message)
//        }
//    }

    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets.Companion.statusBars,
                title = { Text(text = "Furniture Store") },
                actions = {
                    IconButton(onClick = { navController.navigate("profile")},
                        modifier = Modifier
                            .background(Color(0xFFffcb69), shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = { selectedItemIndex = index },
                        label = { Text(text = item.title) },
                        alwaysShowLabel = false,
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (item.badgeCount != null) {
                                        Badge {
                                            Text(text = item.badgeCount.toString())
                                        }
                                    } else if (item.hasNews) {
                                        Badge()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                when (selectedItemIndex) {
                    0 -> HomeContent(navController = navController, categoryState, loading)
                    1 -> OrdersScreen(navController = navController)
                    2 -> CartScreen(navController = navController, cartState)
                    3 -> SearchScreen(navController = navController)
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeContent(navController: NavController, categoryState: CategoryState, loading: Boolean = false) {
    val categories = categoryState.categories

    // List of colors
    val categoryColors = listOf(
        Color(0xFFDDB892),
        Color(0xFFDDBEA9),
        Color(0xFFB7B7A4),
        Color(0xFFA5A58D),
        Color(0xFF6B705C)
    )

        if (loading) {
            CircularIndicator()
        } else {
            Column {
                // Section Title for Categories
                SectionTitle(title = "Categories", onClick = {})

                // LazyRow to Display Categories
                LazyRow(
                    modifier = Modifier.padding(bottom = 20.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(categories) { index, category ->
                        // Cycle colors using modulus operator
                        val color = categoryColors[index % categoryColors.size]

                        CategoryCard(
                            category = category,
                            color = color
                        ) {
                            navController.navigate("categoryScreen/${category.id}")
                        }
                    }
                }

                // Display products under each category
                categories.forEach { category ->
                    CategorySection(
                        categoryName = category.name,
                        products = category.products,
                        navController = navController,
                        onClick = { navController.navigate("categoryScreen/${category.id}") }
                    )
                }
            }
    }
}

@Composable
fun CategorySection(
    onClick: () -> Unit = {},
    categoryName: String,
    products: List<Product>,
    navController: NavController
) {
    SectionTitle(title = categoryName, onClick = onClick)

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products.filter { it.category == categoryName }) { product ->
            ItemCard(product = product) {
                navController.navigate("productDetails/${product.productId}")
            }
        }
    }
}

@Composable
fun CategoryCard(category: Category, color: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(100.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ItemCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.imageUrls[0]),
                contentDescription = product.name,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.name,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${product.price}",
                style = TextStyle(fontSize = 14.sp, color = Color(0xFF5e3023), fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(horizontal = 8.dp)

            )
        }
    }
}


@Composable
fun SectionTitle(title: String, onClick: () -> Unit) {
    Text(
        text = title,
        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(start = 16.dp).clickable(onClick = { onClick() })
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ShoppingAppTheme {
        HomeScreen(navController = rememberNavController(), cartState = CartState(), orderState = OrderState() , productState = ProductState() , categoryState = CategoryState())
    }
}