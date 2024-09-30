package com.example.shoppingapp.views.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ModalType {
    SUCCESS,
    ERROR
}

@Composable
fun CustomModal(
    type: ModalType,
    title: String = "",
    text: String,
    primaryButtonText: String,
    onPrimaryButtonClick: () -> Unit,
    secondaryButtonText: String? = null,
    onSecondaryButtonClick: (() -> Unit)? = null,
    tertiaryButtonText: String? = null,
    onTertiaryButtonClick: (() -> Unit)? = null,
    primaryButtonStyle : ButtonColors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B705C)),
    secondaryButtonStyle : ButtonColors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C6644)),
    tertiaryButtonStyle : ButtonColors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDDBEA9))
) {

    val textColor = when (type) {
        ModalType.SUCCESS -> Color(0xFF155724)  // Dark green for success text
        ModalType.ERROR -> Color(0xFF721C24)    // Dark red for error text
    }

    AlertDialog(
        onDismissRequest = { /* Handle dismiss */ },
        title = {
            Text(
                text = if (title.isNotEmpty()) title else if (type == ModalType.SUCCESS) "Success" else "Error",
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        text = {
            Text(
                text = text,
                color = textColor,
                fontSize = 16.sp
            )
        },
        confirmButton = {
            Button(
                onClick = onPrimaryButtonClick,
                colors = primaryButtonStyle
            ) {
                Text(primaryButtonText)
            }
        },
        dismissButton = {
            Column {
                secondaryButtonText?.let {
                    Button(
                        onClick = onSecondaryButtonClick ?: {},
                        colors = secondaryButtonStyle
                    ) {
                        Text(it)
                    }
                }
                tertiaryButtonText?.let {
                    Button(
                        onClick = onTertiaryButtonClick ?: {},
                        colors = tertiaryButtonStyle
                    ) {
                        Text(it)
                    }
                }
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    )
}

@Preview
@Composable
fun CustomModalPreview() {
    var showModal by remember { mutableStateOf(true) }

    if (showModal) {
        CustomModal(
            type = ModalType.ERROR,
            text = "Your operation was successful!",
            primaryButtonText = "OK",
            onPrimaryButtonClick = { showModal = false },
            secondaryButtonText = "Cancel",
            onSecondaryButtonClick = { showModal = false },
            tertiaryButtonText = "Help",
            onTertiaryButtonClick = { /* Handle help action */ },
            primaryButtonStyle = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B705C)),
            secondaryButtonStyle = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C6644)),
            tertiaryButtonStyle = ButtonDefaults.buttonColors(containerColor = Color(0xFFDDBEA9))
        )
    }
}