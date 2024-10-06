package com.example.shoppingapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.viewmodels.CategoryState
import com.example.shoppingapp.views.components.CustomTopAppBar

@Composable
fun CategoryScreen(navController: NavHostController, categoryId: String?, categoryState: CategoryState) {
    val category = categoryState.categories.find { it.id == categoryId }
    val products = category?.products ?: emptyList()

    CustomTopAppBar(
        title = category?.name ?: "Products",
        onNavigationClick = { navController.popBackStack() },
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.padding(paddingValues)
        ) {
            items(products) { product ->
                ProductCard(product = product) {
                    navController.navigate("productDetails/${product.productId}")
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
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
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            )
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF5e3023),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    CategoryScreen(navController = rememberNavController(), categoryId = "ae4b9c08-775f-4249-a7f9-47f2af16c2f4", CategoryState())
}
