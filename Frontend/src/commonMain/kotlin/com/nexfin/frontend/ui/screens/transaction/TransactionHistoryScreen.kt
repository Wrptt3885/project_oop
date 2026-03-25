package com.nexfin.frontend.ui.screens.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexfin.frontend.network.models.response.TransactionResponse
import com.nexfin.frontend.ui.screens.home.RecentTransactions

@Composable
fun TransactionHistoryScreen(
    transactions: List<TransactionResponse>,
    onBack: () -> Unit
) {
    FormShell(
        title = "Transaction History",
        subtitle = "Review the latest movement across your wallet."
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (transactions.isEmpty()) {
                Text("No transactions yet.", style = MaterialTheme.typography.bodyLarge)
            } else {
                RecentTransactions(transactions)
            }
            com.nexfin.frontend.ui.screens.components.CustomButton(
                text = "Back to Dashboard",
                onClick = onBack
            )
        }
    }
}
