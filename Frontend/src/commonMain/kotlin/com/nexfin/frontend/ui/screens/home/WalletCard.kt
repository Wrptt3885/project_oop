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
            .height(210.dp) // ล็อกความสูงให้สัดส่วนเหมือนบัตรจริง
            .clip(RoundedCornerShape(24.dp))
            // Layer 1: พื้นหลังการ์ดโปร่งแสง (Glass)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0x33FFFFFF), // สีขาวโปร่งแสง 20% มุมซ้ายบน
                        Color(0x05FFFFFF)  // แทบจะใส มุมขวาล่าง
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
            // Layer 2: เหลือบสี Neon สวยๆ
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        NexFinColors.NeonPurple.copy(alpha = 0.2f),
                        NexFinColors.NeonCyan.copy(alpha = 0.1f)
                    )
                )
            )
            // Layer 3: ขอบกระจกวาวๆ (Glass Border)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0x66FFFFFF), // ขอบขาวสว่างด้านบน
                        Color(0x1AFFFFFF)  // ขอบจางด้านล่าง
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
            // Header บัตร
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "VIRTUAL WALLET",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White.copy(alpha = 0.7f),
                    letterSpacing = 1.sp
                )
                // จำลองสัญลักษณ์ชิป หรือ NFC
                Text(
                    text = "»»»", 
                    color = Color.White.copy(alpha = 0.5f), 
                    letterSpacing = 2.sp
                )
            }

            // ตรงกลาง: ยอดเงิน
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Available Balance",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.6f)
                )
                Text(
                    text = CurrencyFormatter.format(balance, currency),
                    // ปรับไซส์ใหญ่ๆ ฟอนต์หนาๆ ให้อ่านง่าย
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }

            // Footer บัตร: ชื่อเจ้าของ และ โลโก้แอป
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = ownerLabel.split("@")[0].uppercase(), // เอาแค่ชื่อหน้า @ มาโชว์เท่ๆ
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.8f),
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = "NEXFIN",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    color = NexFinColors.NeonCyan
                )
            }
        }
    }
}