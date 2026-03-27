// Frontend/src/commonMain/kotlin/com/nexfin/frontend/ui/screens/components/CustomTextField.kt
package com.nexfin.frontend.ui.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false, // <--- เพิ่มตัวนี้เข้ามา
    modifier: Modifier = Modifier
) {
    // State สำหรับจำว่าตอนนี้กำลังเปิดดูรหัสผ่านอยู่หรือเปล่า
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        // ถ้าเป็นช่องรหัสผ่าน และ ไม่ได้กดดูรหัส ให้แปลงเป็นจุดกลมๆ
        visualTransformation = if (isPassword && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        // ใส่ปุ่ม แสดง/ซ่อน ไว้ท้ายช่องกรอก (Trailing Icon)
        trailingIcon = {
            if (isPassword) {
                TextButton(onClick = { passwordVisible = !passwordVisible }) {
                    Text(
                        text = if (passwordVisible) "ซ่อน" else "แสดง",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        shape = MaterialTheme.shapes.medium
    )
}