package com.example.shoppingapp.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.shoppingapp.models.Category


class CategoryState {

    val categories: SnapshotStateList<Category> = mutableStateListOf()

    // Function to add a category
    fun addCategory(category: Category) {
        categories.add(category)
    }

    // Function to add a list of categories
    fun addCategories(categories: List<Category>) {
        this.categories.addAll(categories)
    }

    // Function to remove a category
    fun removeCategory(category: Category) {
        categories.remove(category)
    }

    // Function to get a category by id
    fun getCategoryById(id: String): Category? {
        return categories.find { it.id == id }
    }

    // Function to get a category by id
    fun getCategoryNameById(id: String): String {
        return categories.find { it.id == id }?.name ?: ""
    }

    // Function to clear all categories
    fun clear() {
        categories.clear()
    }


}