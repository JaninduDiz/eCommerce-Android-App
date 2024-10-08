package com.example.shoppingapp.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf

data class CartItem(val product: Product, var quantity: MutableState<Int> = mutableIntStateOf(1))