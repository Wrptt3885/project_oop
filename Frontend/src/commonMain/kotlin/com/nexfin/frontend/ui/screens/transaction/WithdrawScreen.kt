package com.nexfin.frontend.ui.screens.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nexfin.frontend.ui.screens.components.CustomTextField

@Composable
fun WithdrawScreen(
    bankAccount: String,
    amount: String,
    isLoading: Boolean,
    onBankAccountChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit
) {
    FormShell(
        title = "ถอนเงินเข้าบัญชีธนาคาร",
        subtitle = "ระบุเลขบัญชีปลายทางและจำนวนเงินที่ต้องการถอน",
        onBackClick = onBack
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp) // เว้นระยะห่างแต่ละช่อง 16.dp
        ) {
            // ช่องกรอกเลขบัญชี
            CustomTextField(
                value = bankAccount,
                onValueChange = onBankAccountChange,
                label = "เลขบัญชีธนาคาร 10 หลัก"
            )
            
            // ช่องกรอกจำนวนเงิน
            CustomTextField(
                value = amount,
                onValueChange = onAmountChange,
                label = "จำนวนเงิน (THB)"
            )

            Spacer(modifier = Modifier.height(16.dp)) // ดันปุ่มให้ห่างลงมาหน่อย

            // ปุ่มกดยืนยัน
            Button(
                onClick = onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF59E0B), // สีส้มทอง
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (isLoading) "กำลังดำเนินการ..." else "ยืนยันการถอนเงิน", 
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}