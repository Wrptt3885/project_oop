package com.nexfin.frontend.ui.screens.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexfin.frontend.ui.screens.components.CustomButton
import com.nexfin.frontend.ui.screens.components.CustomTextField
import com.nexfin.frontend.ui.screens.components.LoadingIndicator

@Composable
fun TransferScreen(
    toUserId: String,
    amount: String,
    reference: String,
    isLoading: Boolean,
    onToUserIdChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onReferenceChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit
) {
    FormShell(
        title = "Transfer Funds",
        subtitle = "Send money using the recipient user ID and keep a clean internal reference."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            CustomTextField(toUserId, onToUserIdChange, "Recipient user ID")
            CustomTextField(amount, onAmountChange, "Amount")
            CustomTextField(reference, onReferenceChange, "Reference")
            CustomButton(
                text = if (isLoading) "Sending..." else "Send Transfer",
                onClick = onSubmit,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
            if (isLoading) LoadingIndicator("Sending transfer")
            Text("Want to head back first?", style = MaterialTheme.typography.bodyMedium)
            CustomButton("Back", onBack, modifier = Modifier.fillMaxWidth(), enabled = !isLoading)
        }
    }
}
