package com.nexfin.frontend.ui.screens.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexfin.frontend.utils.CurrencyFormatter

@Composable
fun SlipScreen(
    amount: String,
    toUserId: String,
    reference: String,
    onBack: () -> Unit
) {
    // กำหนด Box นอกสุดให้ดึงสีพื้นหลังของแอปมาใช้ จะได้ไม่เป็นสีขาวโพลน
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 400.dp) // จำกัดความกว้างไม่ให้ยืดเป็นทีวีไวด์สกรีน
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Receipt Card (ตัวใบสลิป)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Success Icon
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF10B981).copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓", 
                        color = Color(0xFF10B981), 
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Transaction Complete", 
                    color = Color(0xFF10B981), 
                    style = MaterialTheme.typography.titleLarge, 
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                Spacer(modifier = Modifier.height(24.dp))

                // รายละเอียดบิล
                val formattedAmount = amount.toDoubleOrNull()?.let { CurrencyFormatter.format(it) } ?: amount
                ReceiptRow("Amount", "THB $formattedAmount", isBold = true)
                Spacer(modifier = Modifier.height(16.dp))
                ReceiptRow("Destination", toUserId)
                Spacer(modifier = Modifier.height(16.dp))
                ReceiptRow("Reference", reference)

                Spacer(modifier = Modifier.height(32.dp))
                
                // ลายน้ำแอป
                Text(
                    text = "NexFin Wallet", 
                    color = MaterialTheme.colorScheme.primary, 
                    style = MaterialTheme.typography.labelMedium
                )
            }

            // ปุ่ม Save Slip
            Button(
                onClick = { /* TODO: โค้ดเซฟรูปลงเครื่อง */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Save Slip", fontWeight = FontWeight.Bold)
            }

            // ปุ่ม Back To Home
            TextButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Back To Home", color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}


@Composable
private fun ReceiptRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label, 
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), 
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            color = if (isBold) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            style = if (isBold) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
    }
}