// Frontend/src/commonMain/kotlin/com/nexfin/frontend/ui/screens/profile/ProfileScreen.kt
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
import androidx.compose.ui.text.font.FontWeight
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
        title = "โปรไฟล์บัญชี",
        subtitle = "ข้อมูลภาพรวมของบัญชีที่เข้าสู่ระบบในปัจจุบัน",
        onBackClick = onBack
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            ProfileInfoCard(
                label = "อีเมลที่เข้าสู่ระบบ",
                value = email.ifBlank { "ไม่พบข้อมูลอีเมล" }
            )
            ProfileInfoCard(
                label = "รหัสผู้ใช้ (User ID)",
                value = userId.ifBlank { "ไม่พบรหัสผู้ใช้" }
            )
            // ปุ่มออกจากระบบ
            CustomButton(
                text = "ออกจากระบบ", 
                onClick = onLogout, 
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )
        }
    }
}

// คอมโพเนนต์การ์ดข้อมูลที่แก้ไขสีให้สว่างแล้ว
@Composable
private fun ProfileInfoCard(
    label: String,
    value: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface) // พื้นการ์ดสีน้ำเงินเข้ม
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            // หัวข้อ (Label) ใช้สีฟ้า Secondary ให้ดูมีลูกเล่น
            Text(
                text = label, 
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary 
            )
            // ตัวข้อมูล (Value) บังคับให้เป็นสีขาว/สว่าง และหนาขึ้น
            Text(
                text = value, 
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface 
            )
        }
    }
}