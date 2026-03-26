// Frontend/src/commonMain/kotlin/com/nexfin/frontend/ui/screens/transaction/FormShell.kt
package com.nexfin.frontend.ui.screens.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FormShell(
    title: String,
    onBackClick: () -> Unit,
    content: @Composable () -> Unit
) {
    // กางเต็มจอ + ดูดสีดำ
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        // ล็อกความกว้างไว้ตรงกลาง (Responsive)
        Column(
            modifier = Modifier
                .widthIn(max = 500.dp) // ฟอร์มกรอกข้อมูลไม่ควรใหญ่เกิน 500dp
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Header: ปุ่ม Back + ชื่อหน้า
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                TextButton(onClick = onBackClick) {
                    Text("❮ Back", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // ตัวการ์ดสำหรับใส่ฟอร์ม
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(24.dp)
            ) {
                content()
            }
        }
    }
}