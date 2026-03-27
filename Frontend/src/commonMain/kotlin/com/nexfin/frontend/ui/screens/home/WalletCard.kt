// Frontend/src/commonMain/kotlin/com/nexfin/frontend/ui/screens/home/WalletCard.kt
package com.nexfin.frontend.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexfin.frontend.ui.theme.NexFinColors
import com.nexfin.frontend.utils.CurrencyFormatter

@Composable
fun WalletCard(
    balance: Double,
    currency: String,
    ownerLabel: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .clip(RoundedCornerShape(24.dp))
            // Layer 1: พื้นหลังการ์ดโปร่งแสง
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0x33FFFFFF),
                        Color(0x05FFFFFF)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
            // Layer 2: เปลี่ยนจาก Neon มาเป็นโทน Premium Blue
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        NexFinColors.PrimaryAccent.copy(alpha = 0.4f),   // น้ำเงินเข้ม
                        NexFinColors.SecondaryAccent.copy(alpha = 0.2f)  // ฟ้าสว่าง
                    )
                )
            )
            // Layer 3: ขอบกระจกวาวๆ (Glass Border)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0x66FFFFFF),
                        Color(0x1AFFFFFF)
                    )
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "VIRTUAL WALLET",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White.copy(alpha = 0.8f),
                    letterSpacing = 1.sp
                )
                Text(
                    text = "»»»", 
                    color = Color.White.copy(alpha = 0.6f), 
                    letterSpacing = 2.sp
                )
            }

            // ตรงกลาง: ยอดเงิน
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "ยอดเงินคงเหลือ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Text(
                    text = CurrencyFormatter.format(balance, currency),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }

            // Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = ownerLabel.split("@")[0].uppercase(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.9f),
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = "NEXFIN",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    color = NexFinColors.SecondaryAccent // เปลี่ยนเป็นสีฟ้า Sky Blue
                )
            }
        }
    }
}