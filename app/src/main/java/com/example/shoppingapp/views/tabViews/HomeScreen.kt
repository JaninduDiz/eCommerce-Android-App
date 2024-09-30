package com.example.shoppingapp.views.tabViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import com.example.shoppingapp.viewmodels.CartState
import com.example.shoppingapp.models.Category
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.models.bedroomFurnitureCategory
import com.example.shoppingapp.models.diningFurnitureCategory
import com.example.shoppingapp.models.gamingFurnitureCategory
import com.example.shoppingapp.models.livingRoomFurnitureCategory
import com.example.shoppingapp.models.officeFurnitureCategory
import com.example.shoppingapp.models.sampleProducts
import com.example.shoppingapp.viewmodels.OrderState

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

val categoryColors = mapOf(
    officeFurnitureCategory to Color(0xFFDDB892),
    livingRoomFurnitureCategory to Color(0xFFDDBEA9),
    diningFurnitureCategory to Color(0xFFB7B7A4),
    bedroomFurnitureCategory to Color(0xFFA5A58D),
    gamingFurnitureCategory to Color(0xFF6B705C)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, cartState: CartState, orderState: OrderState) {
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

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

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
                    0 -> HomeContent(navController = navController)
                    1 -> OrdersScreen(navController = navController)
                    2 -> CartScreen(navController = navController, cartState, orderState)
                    3 -> SearchScreen(navController = navController)
                }
            }
        }
    }
}

@Composable
fun HomeContent(navController: NavController) {
    SectionTitle(title = "Trending Items")
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sampleProducts.take(5)) { product ->
            ItemCard(product = product) {
                navController.navigate("productDetails/${product.productId}")
            }
        }
    }

    SectionTitle(title = "Categories")
    LazyRow(
        modifier = Modifier.padding(bottom = 20.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categoryColors.keys.toList()) { category ->
            CategoryCard(
                category = category,
                color = categoryColors[category] ?: Color.Gray
            ) {
                navController.navigate("categoryScreen/${category.categoryId}")
            }
        }
    }

    SectionTitle(title = "Home Appliances")
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sampleProducts.filter { it.category == livingRoomFurnitureCategory || it.category == diningFurnitureCategory }) { product ->
            ItemCard(product = product) {
                navController.navigate("productDetails/${product.productId}")
            }
        }
    }

    SectionTitle(title = "Office Furniture")
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sampleProducts.filter { it.category == officeFurnitureCategory }) { product ->
            ItemCard(product = product) {
                navController.navigate("productDetails/${product.productId}")
            }
        }
    }

    SectionTitle(title = "Bedroom Furniture")
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sampleProducts.filter { it.category == bedroomFurnitureCategory }) { product ->
            ItemCard(product = product) {
                navController.navigate("productDetails/${product.productId}")
            }
        }
    }

    SectionTitle(title = "Gaming Furniture")
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sampleProducts.filter { it.category == gamingFurnitureCategory }) { product ->
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
                painter = painterResource(id = R.drawable.ic_launcher_background), // Placeholder image
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
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(start = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ShoppingAppTheme {
        HomeScreen(navController = rememberNavController(), cartState = CartState(), orderState = OrderState())
    }
}