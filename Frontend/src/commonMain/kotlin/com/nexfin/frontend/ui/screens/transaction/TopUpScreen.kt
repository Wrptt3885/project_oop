package com.nexfin.frontend.ui.screens.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexfin.frontend.ui.screens.components.CustomButton
import com.nexfin.frontend.ui.screens.components.CustomTextField
import com.nexfin.frontend.ui.screens.components.LoadingIndicator

@Composable
fun TopUpScreen(
    amount: String,
    reference: String,
    isLoading: Boolean,
    onAmountChange: (String) -> Unit,
    onReferenceChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit
) {
    FormShell(
        title = "Top Up Wallet",
        subtitle = "Add funds instantly with a clean reference you can track later."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            CustomTextField(amount, onAmountChange, "Amount")
            CustomTextField(reference, onReferenceChange, "Reference")
            CustomButton(
                text = if (isLoading) "Processing..." else "Confirm Top Up",
                onClick = onSubmit,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
            if (isLoading) LoadingIndicator("Applying top-up")
            Text("Need to adjust something first?", style = MaterialTheme.typography.bodyMedium)
            CustomButton("Back", onBack, modifier = Modifier.fillMaxWidth(), enabled = !isLoading)
        }
    }
}
