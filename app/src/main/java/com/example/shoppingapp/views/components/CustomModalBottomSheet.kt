
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
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

    ModalBottomSheet(
        onDismissRequest = {
            coroutineScope.launch { sheetState.hide() } // Close the sheet
            onDismiss()
        },
        sheetState = sheetState,
        modifier = Modifier.fillMaxHeight(0.5f),  // Optional height customization
    ) {
        // This is the content area inside the modal
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f), // Content takes most of the space
            contentAlignment = Alignment.TopStart
        ) {
            sheetContent()  // Render the dynamic content passed via the children prop
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fixed button at the bottom
        Button(
            onClick = {
                coroutineScope.launch { sheetState.hide() }
                onButtonClick() // Perform the action when the button is clicked
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(buttonText)
        }
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