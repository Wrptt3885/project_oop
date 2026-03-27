package com.nexfin.frontend.ui.screens.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexfin.frontend.network.models.response.TransactionResponse
import com.nexfin.frontend.ui.screens.home.EmptyStateCard
import com.nexfin.frontend.ui.screens.home.TransactionCard

@Composable
fun TransactionHistoryScreen(
    transactions: List<TransactionResponse>,
    onBack: () -> Unit,
    onTransactionClick: (TransactionResponse) -> Unit
) {
    FormShell(
        title = "Transaction History",
        subtitle = "Review every wallet movement in one scrollable list.",
        onBackClick = onBack
    ) {
        if (transactions.isEmpty()) {
            EmptyStateCard("No transactions yet. Your completed top-ups and transfers will show up here.")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "All Transactions",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                items(
                    items = transactions,
                    key = { transaction -> transaction.transactionId }
                ) { transaction ->
                    TransactionCard(
                        transaction = transaction,
                        onClick = { onTransactionClick(transaction) }
                    )
                }
            }
        }
    }
}
