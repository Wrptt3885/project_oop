// Frontend/src/commonMain/kotlin/com/nexfin/frontend/ui/screens/home/DashboardScreen.kt
package com.nexfin.frontend.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexfin.frontend.network.models.response.TransactionResponse
import com.nexfin.frontend.network.models.response.WalletResponse
import com.nexfin.frontend.ui.screens.components.LoadingIndicator

@Composable
fun DashboardScreen(
    wallet: WalletResponse?,
    transactions: List<TransactionResponse>,
    userEmail: String,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onTopUpNavigate: () -> Unit,
    onTransferNavigate: () -> Unit,
    onHistoryNavigate: () -> Unit,
    onProfileNavigate: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "NexFin Wallet",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary // ใช้สีฟ้า Sky Blue
                    )
                    Text(
                        text = "สวัสดี, ${userEmail.substringBefore("@")}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                
                FilledTonalButton(
                    onClick = onProfileNavigate,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("โปรไฟล์")
                }
            }

            // Wallet Card
            if (isLoading && wallet == null) {
                LoadingIndicator("กำลังโหลดข้อมูลกระเป๋าเงิน...")
            } else {
                WalletCard(
                    balance = wallet?.balance ?: 0.0,
                    currency = wallet?.currency ?: "THB",
                    ownerLabel = userEmail
                )
            }

            // Fast Actions
            Text(
                text = "ธุรกรรมด่วน",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onTopUpNavigate,
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary, // สีน้ำเงินเข้ม
                        contentColor = Color.White
                    )
                ) {
                    Text("เติมเงิน", fontWeight = FontWeight.Bold)
                }
                
                Button(
                    onClick = onTransferNavigate,
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary, // สีฟ้า
                        contentColor = Color.White
                    )
                ) {
                    Text("โอนเงิน", fontWeight = FontWeight.Bold)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onHistoryNavigate,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onBackground)
                ) {
                    Text("ประวัติรายการ")
                }
                
                OutlinedButton(
                    onClick = onRefresh,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onBackground)
                ) {
                    Text(if (isLoading) "กำลังรีเฟรช..." else "รีเฟรชข้อมูล")
                }
            }

            // Recent Activity (บังคับสีขาวให้ชัดเจน)
            Text(
                text = "ประวัติการทำรายการล่าสุด",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground, // ขาวสว่าง
                modifier = Modifier.padding(top = 16.dp)
            )
            
            Box(modifier = Modifier.padding(top = 8.dp)) {
                if (transactions.isEmpty()) {
                    // กล่องข้อความตอนไม่มีข้อมูล บังคับสีให้สวยๆ
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "ยังไม่มีประวัติการทำธุรกรรม...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f) // เทาอ่อน
                        )
                    }
                } else {
                    RecentTransactions(transactions)
                }
            }
        }
    }
}