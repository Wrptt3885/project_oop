package com.nexfin.frontend.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nexfin.frontend.ui.screens.components.CustomButton
import com.nexfin.frontend.ui.screens.transaction.FormShell

@Composable
fun ProfileScreen(
    email: String,
    userId: String,
    onLogout: () -> Unit,
    onBack: () -> Unit
) {
    FormShell(
        title = "Profile",
        subtitle = "A simple overview of the current signed-in session."
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            ProfileInfoCard(
                label = "Signed-in Email",
                value = email.ifBlank { "No email available" }
            )
            ProfileInfoCard(
                label = "User ID",
                value = userId.ifBlank { "No session user ID" }
            )
            CustomButton("Log Out", onLogout, modifier = Modifier.fillMaxWidth())
            CustomButton("Back to Dashboard", onBack, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun ProfileInfoCard(
    label: String,
    value: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(label, style = MaterialTheme.typography.labelLarge)
            Text(value, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
