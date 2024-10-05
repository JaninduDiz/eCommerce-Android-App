
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.example.shoppingapp.views.components.CustomButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomModalBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false), // Control the modal state
    onDismiss: () -> Unit = {},  // Dismiss callback
    sheetContent: @Composable () -> Unit,  // Content to render inside the modal
    buttonText: String = "Confirm",  // Button text
    onButtonClick: () -> Unit,  // Callback for the button click
) {
    val coroutineScope = rememberCoroutineScope()

    // Detect if the keyboard is visible
    val isKeyboardVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    // Adjust height of the bottom sheet based on the keyboard visibility
    val bottomSheetHeight = if (isKeyboardVisible) Modifier.fillMaxHeight(0.7f) else Modifier.fillMaxHeight(0.5f)

    ModalBottomSheet(
        onDismissRequest = {
            coroutineScope.launch { sheetState.hide() }
            onDismiss()
        },
        sheetState = sheetState,
        modifier = bottomSheetHeight,  // Dynamic height based on keyboard visibility
    ) {
        // Box containing the sheet content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f),
            contentAlignment = Alignment.TopStart
        ) {
            sheetContent()
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Button at the bottom
        CustomButton(
            text = buttonText,
            onClick = {
                coroutineScope.launch { sheetState.hide() }
                onButtonClick()
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CustomModalBottomSheetPreview() {
    ShoppingAppTheme {
        CustomModalBottomSheet(
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
            onDismiss = { /* Handle dismiss */ },
            sheetContent = {
                Column {
                    Text("This is a dynamic content inside the bottom sheet")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("You can render anything here.")
                }
            },
            buttonText = "Confirm",
            onButtonClick = { /* Handle button click */ }
        )
    }
}