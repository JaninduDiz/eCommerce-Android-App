package com.example.shoppingapp.views.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    onNavigationClick: () -> Unit,
    showActionIcon: Boolean = false,
    actionIcon: @Composable (() -> Unit)? = null,
    centeredHeader: Boolean = false,
    isHeaderPinned: Boolean = false,
    content: @Composable (PaddingValues) -> Unit
) {
    // Apply scrolling behavior only when isHeaderPinned is false
    val scrollBehavior = if (isHeaderPinned) {
        null  // No scrolling behavior when pinned
    } else {
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    }

    Scaffold(
        modifier = if (!isHeaderPinned) Modifier.nestedScroll(scrollBehavior!!.nestedScrollConnection) else Modifier,
        topBar = {
            if (centeredHeader) {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFFa2d2ff),
                        titleContentColor = Color(0xFF000000),
                    ),
                    title = {
                        Text(
                            text = if (title.length > 24) title.take(24) + "..." else title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,

                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigationClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        if (showActionIcon) {
                            actionIcon?.invoke()
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            } else {
                MediumTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFDDBEA9).copy(alpha = 0.8f),
                        titleContentColor = Color(0xFF000000),

                        ),
                    title = {
                        Text(
                            text = title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigationClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        if (showActionIcon) {
                            actionIcon?.invoke()
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        },
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Preview
@Composable
fun CustomTopAppBarPreview() {
    CustomTopAppBar(
        title = "Custom Top App Bar",
        onNavigationClick = { /* do something */ },
        showActionIcon = true,
        actionIcon = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu"
                )
            }
        },
        centeredHeader = true // Toggle between true/false to see different header types
    ) { innerPadding ->
        // Example content to demonstrate how `innerPadding` works
        ScrollContent(innerPadding)
    }
}

@Composable
fun ScrollContent(innerPadding: PaddingValues) {
    // Your scrollable content or other UI elements go here, applying the padding as needed
}