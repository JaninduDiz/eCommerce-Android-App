package com.example.shoppingapp.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
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
        Log.d(TAG, "addCategories: ${categories.size} ")

    }

    // Function to remove a category
    fun removeCategory(category: Category) {
        categories.remove(category)
    }

    // Function to get a category by id
    fun getCategoryById(id: String): Category? {
        return categories.find { it.id == id }
    }

    // Function to clear all categories
    fun clear() {
        categories.clear()
    }


}