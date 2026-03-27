// Frontend/src/commonMain/kotlin/com/nexfin/frontend/ui/screens/transaction/TransactionHistoryScreen.kt
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
        title = "ประวัติการทำธุรกรรม",
        subtitle = "ตรวจสอบรายการเคลื่อนไหวล่าสุดในวอลเล็ทของคุณ",
        onBackClick = onBack
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (transactions.isEmpty()) {
                Text("ยังไม่มีประวัติการทำธุรกรรมในขณะนี้", style = MaterialTheme.typography.bodyLarge)
            } else {
                RecentTransactions(transactions)
            }
        }
    }
}