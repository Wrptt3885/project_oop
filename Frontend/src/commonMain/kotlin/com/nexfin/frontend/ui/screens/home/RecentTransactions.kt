package com.nexfin.frontend.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexfin.frontend.network.models.response.TransactionResponse
import com.nexfin.frontend.utils.CurrencyFormatter
import com.nexfin.frontend.utils.DateFormatter

@Composable
fun RecentTransactions(
    transactions: List<TransactionResponse>,
    onTransactionClick: ((TransactionResponse) -> Unit)? = null
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (transactions.isEmpty()) {
            EmptyStateCard("ยังไม่มีประวัติการทำธุรกรรม...")
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                transactions.take(5).forEach { transaction ->
                    TransactionCard(
                        transaction = transaction,
                        onClick = onTransactionClick?.let { callback -> { callback(transaction) } }
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionCard(
    transaction: TransactionResponse,
    onClick: (() -> Unit)? = null
) {
    // แยกประเภท เติมเงิน (TOPUP) หรือ โอนออก (TRANSFER)
    val isTopUp = transaction.type.equals("TOPUP", ignoreCase = true)
    val iconColor = if (isTopUp) Color(0xFF10B981) else Color(0xFFEF4444) // เขียว (เข้า) / แดง (ออก)
    val amountPrefix = if (isTopUp) "+" else "-"
    val typeLabel = if (isTopUp) "เติมเงินเข้าวอลเล็ท" else "โอนเงินออก"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .then(
                if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // กล่องไอคอนลูกศร
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isTopUp) "↓" else "↑",
                    color = iconColor,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black
                )
            }

            // ข้อมูลรายการ
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = typeLabel,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = DateFormatter.compact(transaction.createdAt),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }

        // ยอดเงิน และ สถานะ
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$amountPrefix${CurrencyFormatter.format(transaction.amount)}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = iconColor
            )
            Text(
                text = transaction.status,
                style = MaterialTheme.typography.bodyMedium,
                color = if (transaction.status == "SUCCESS") Color(0xFF10B981) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun EmptyStateCard(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
    }
}