// Frontend/src/commonMain/kotlin/com/nexfin/frontend/ui/screens/auth/RegisterScreen.kt
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
fun RegisterScreen(
    fullName: String, // <--- เอากลับมาแล้ว!
    email: String,
    password: String,
    isLoading: Boolean,
    onFullNameChange: (String) -> Unit, // <--- เอากลับมาแล้ว!
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onLoginNavigate: () -> Unit
) {
    AuthShell(
        title = "สร้างบัญชีใหม่",
        subtitle = "สมัครสมาชิก NexFin เพื่อเริ่มต้นจัดการเงินดิจิทัลของคุณอย่างง่ายดาย"
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            // ช่องกรอกข้อมูล
            CustomTextField(fullName, onFullNameChange, "ชื่อ-นามสกุล") // <--- เพิ่มช่องนี้เข้ามา
            CustomTextField(email, onEmailChange, "อีเมล")
            CustomTextField(password, onPasswordChange, "รหัสผ่าน")

            // ปุ่มสมัครสมาชิก
            CustomButton(
                text = if (isLoading) "กำลังสร้างบัญชี..." else "สมัครสมาชิก",
                onClick = onSubmit,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            // ข้อความคั่น
            Text(
                text = "มีบัญชีอยู่แล้วใช่ไหม?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 16.dp)
            )

            // ปุ่มกลับไปหน้าเข้าสู่ระบบ
            CustomButton(
                text = "เข้าสู่ระบบ",
                onClick = onLoginNavigate,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
        }
    }
}