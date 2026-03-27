package com.nexfin.frontend.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexfin.frontend.ui.screens.components.CustomButton
import com.nexfin.frontend.ui.screens.components.CustomTextField

@Composable
fun LoginScreen(
    email: String,
    password: String,
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onRegisterNavigate: () -> Unit
) {
    AuthShell(
        title = "ยินดีต้อนรับกลับมา",
        subtitle = "เข้าสู่ระบบอย่างปลอดภัยเพื่อจัดการยอดเงิน โอนเงิน และดูประวัติการทำรายการ"
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            // ช่องกรอกข้อมูล
            CustomTextField(email, onEmailChange, "อีเมล")
            CustomTextField(password, onPasswordChange, "รหัสผ่าน", isPassword = true)

            // ปุ่มเข้าสู่ระบบ
            CustomButton(
                text = if (isLoading) "กำลังเข้าสู่ระบบ..." else "เข้าสู่ระบบ",
                onClick = onSubmit,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            // ข้อความคั่น
            Text(
                text = "ยังไม่มีบัญชีใช่ไหม?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 16.dp)
            )

            // ปุ่มไปหน้าสมัครสมาชิก
            CustomButton(
                text = "สร้างบัญชีใหม่",
                onClick = onRegisterNavigate,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
        }
    }
}