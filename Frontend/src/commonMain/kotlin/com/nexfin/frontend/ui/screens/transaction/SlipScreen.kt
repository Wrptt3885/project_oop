package com.nexfin.frontend.ui.screens.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexfin.frontend.ui.screens.components.CustomButton
import com.nexfin.frontend.utils.AppLogger
import com.nexfin.frontend.utils.rememberImageSaver
import kotlinx.coroutines.launch

@Composable
fun SlipScreen(
    amount: String,
    toUserId: String,
    reference: String,
    onBack: () -> Unit
) {
    val graphicsLayer = rememberGraphicsLayer()
    val coroutineScope = rememberCoroutineScope()
    val imageSaver = rememberImageSaver()
    var saveMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    graphicsLayer.record {
                        this@drawWithContent.drawContent()
                    }
                    drawLayer(graphicsLayer)
                }
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF172033))
                .padding(32.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Transaction Complete",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF10B981),
                    fontWeight = FontWeight.Bold
                )

                HorizontalDivider(color = Color.White.copy(alpha = 0.2f))

                ReceiptRow("Amount", "THB $amount")
                ReceiptRow("Destination", toUserId)
                ReceiptRow("Reference", reference.ifBlank { "-" })

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "NexFin Wallet",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(0xFF0EA5E9)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        CustomButton(
            text = "Save Slip",
            onClick = {
                coroutineScope.launch {
                    try {
                        val bitmap = graphicsLayer.toImageBitmap()
                        val success = imageSaver.saveToGallery(
                            bitmap = bitmap,
                            fileName = "NexFin_Slip_${System.currentTimeMillis()}"
                        )
                        saveMessage = if (success) {
                            AppLogger.info("SlipScreen", "Slip saved successfully.")
                            "Slip saved successfully. Check your gallery."
                        } else {
                            AppLogger.error("SlipScreen", "Slip save returned false.")
                            "Failed to save slip."
                        }
                    } catch (error: Exception) {
                        AppLogger.error("SlipScreen", "Failed to save slip.", error)
                        saveMessage = "An error occurred while saving the slip."
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onBack) {
            Text("Back To Home", color = MaterialTheme.colorScheme.onBackground)
        }

        saveMessage?.let { message ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                color = if (message.contains("successfully")) Color(0xFF10B981) else Color.Red
            )
        }
    }
}

@Composable
fun ReceiptRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.White.copy(alpha = 0.6f))
        Text(value, color = Color.White, fontWeight = FontWeight.SemiBold)
    }
}
