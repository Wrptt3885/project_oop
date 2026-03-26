// Frontend/src/commonMain/kotlin/com/nexfin/frontend/ui/screens/home/DashboardScreen.kt
package com.nexfin.frontend.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    // Layer 1: พื้นหลังดำสนิทกางเต็มจอ
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        // Layer 2: ตัว Content หลัก ล็อกความกว้างไว้ตรงกลาง
        Column(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            
            // --- Header: ทักทายผู้ใช้ ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "NexFin Wallet",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Hi, ${userEmail.substringBefore("@")}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                
                // ปุ่ม Profile
                FilledTonalButton(
                    onClick = onProfileNavigate,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Profile")
                }
            }

            // --- Wallet Card ---
            if (isLoading && wallet == null) {
                LoadingIndicator("Loading your dashboard")
            } else {
                WalletCard(
                    balance = wallet?.balance ?: 0.0,
                    currency = wallet?.currency ?: "THB",
                    ownerLabel = userEmail
                )
            }

            // --- Fast Actions ---
            Text(
                text = "Fast Actions",
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
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Top Up", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                }
                
                Button(
                    onClick = onTransferNavigate,
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Transfer", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondary)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onHistoryNavigate,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("History")
                }
                
                OutlinedButton(
                    onClick = onRefresh,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(if (isLoading) "Refreshing..." else "Refresh")
                }
            }

            // --- Recent Activity (ดึงจากไฟล์เดิมของมึงมาโชว์เลย) ---
            Box(modifier = Modifier.padding(top = 16.dp)) {
                RecentTransactions(transactions)
            }
        }
    }
}