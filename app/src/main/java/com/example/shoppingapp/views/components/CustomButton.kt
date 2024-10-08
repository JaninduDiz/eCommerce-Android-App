package com.example.shoppingapp.views.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton( text: String, onClick: () -> Unit, modifier: Modifier, enabled: Boolean? = true, colors: ButtonColors = ButtonDefaults.buttonColors()) {
    Button(
        onClick = { onClick() },
        colors = colors,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .then(modifier),
        shape = RoundedCornerShape(8.dp),
        enabled = enabled ?: true
    ) {
        Text(text = text, color = Color.White, fontSize = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun CustomButtonPreview() {
    CustomButton(text = "Confirm Order", onClick = { /* Handle confirm order */ }, modifier = Modifier, enabled = true)
}