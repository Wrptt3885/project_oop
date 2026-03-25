package com.nexfin.frontend.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nexfin.frontend.network.models.response.TransactionResponse
import com.nexfin.frontend.utils.CurrencyFormatter
import com.nexfin.frontend.utils.DateFormatter

@Composable
fun RecentTransactions(
    transactions: List<TransactionResponse>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Recent Activity", style = MaterialTheme.typography.titleLarge)
        if (transactions.isEmpty()) {
            EmptyStateCard("No transactions yet. Your latest top-up and transfer will appear here.")
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                transactions.take(5).forEach { transaction ->
                    TransactionRow(transaction)
                }
            }
        }
    }
}

@Composable
private fun TransactionRow(transaction: TransactionResponse) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(transaction.type, style = MaterialTheme.typography.labelLarge)
            Text(transaction.reference, style = MaterialTheme.typography.bodyMedium)
            Text(DateFormatter.compact(transaction.createdAt), style = MaterialTheme.typography.bodyMedium)
        }
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(CurrencyFormatter.format(transaction.amount), style = MaterialTheme.typography.labelLarge)
            Text(transaction.status, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun EmptyStateCard(message: String) {
    Text(
        text = message,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.86f))
            .padding(18.dp),
        style = MaterialTheme.typography.bodyMedium
    )
}
