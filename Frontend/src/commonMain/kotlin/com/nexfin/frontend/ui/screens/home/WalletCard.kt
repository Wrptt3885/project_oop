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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.nexfin.frontend.ui.theme.NexFinColors
import com.nexfin.frontend.utils.CurrencyFormatter

@Composable
fun WalletCard(
    balance: Double,
    currency: String,
    ownerLabel: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(
                        NexFinColors.Forest,
                        NexFinColors.Forest.copy(alpha = 0.94f),
                        NexFinColors.Seafoam.copy(alpha = 0.92f)
                    )
                )
            )
            .padding(22.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Available Balance",
            style = MaterialTheme.typography.bodyMedium,
            color = NexFinColors.Mist
        )
        Text(
            text = CurrencyFormatter.format(balance, currency),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(ownerLabel, color = NexFinColors.Mist)
            Text("Primary $currency Wallet", color = NexFinColors.Gold)
        }
    }
}
