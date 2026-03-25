package com.nexfin.frontend.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nexfin.frontend.network.models.response.TransactionResponse
import com.nexfin.frontend.network.models.response.WalletResponse
import com.nexfin.frontend.ui.screens.components.CustomButton
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(28.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("NexFin Wallet", style = MaterialTheme.typography.titleLarge)
            Text("Hi, $userEmail", style = MaterialTheme.typography.headlineMedium)
            Text(
                "A calm place to check balance, top up fast, and move money with confidence.",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DashboardChip("Live Balance", "Realtime wallet snapshot")
            DashboardChip("Fast Actions", "Top up or transfer in seconds")
        }

        if (isLoading && wallet == null) {
            LoadingIndicator("Loading your dashboard")
        } else {
            WalletCard(
                balance = wallet?.balance ?: 0.0,
                currency = wallet?.currency ?: "THB",
                ownerLabel = userEmail
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            CustomButton("Top Up", onTopUpNavigate, modifier = Modifier.fillMaxWidth())
            CustomButton("Transfer", onTransferNavigate, modifier = Modifier.fillMaxWidth())
            CustomButton("History", onHistoryNavigate, modifier = Modifier.fillMaxWidth())
            CustomButton("Profile", onProfileNavigate, modifier = Modifier.fillMaxWidth())
            CustomButton("Refresh", onRefresh, modifier = Modifier.fillMaxWidth())
        }

        RecentTransactions(transactions)
    }
}

@Composable
private fun DashboardChip(
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(title, style = MaterialTheme.typography.labelLarge)
        Text(subtitle, style = MaterialTheme.typography.bodyMedium)
    }
}
