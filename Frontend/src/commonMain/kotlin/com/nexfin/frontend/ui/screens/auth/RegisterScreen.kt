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
    fullName: String,
    email: String,
    password: String,
    isLoading: Boolean,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onLoginNavigate: () -> Unit
) {
    AuthShell(
        title = "สร้างบัญชีใหม่",
        subtitle = "สมัครสมาชิก NexFin เพื่อเริ่มต้นจัดการการเงินดิจิทัลของคุณอย่างง่ายดาย"
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            CustomTextField(fullName, onFullNameChange, "ชื่อ-นามสกุล")
            CustomTextField(email, onEmailChange, "อีเมล")
            CustomTextField(password, onPasswordChange, "รหัสผ่าน", isPassword = true)

            CustomButton(
                text = if (isLoading) "กำลังสร้างบัญชี..." else "สมัครสมาชิก",
                onClick = onSubmit,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            Text(
                text = "มีบัญชีอยู่แล้วใช่ไหม?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 16.dp)
            )

            CustomButton(
                text = "กลับไปเข้าสู่ระบบ",
                onClick = onLoginNavigate,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
        }
    }
}
