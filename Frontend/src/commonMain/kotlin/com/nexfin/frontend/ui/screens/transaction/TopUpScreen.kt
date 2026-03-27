// Frontend/src/commonMain/kotlin/com/nexfin/frontend/ui/screens/transaction/TopUpScreen.kt
package com.nexfin.frontend.ui.screens.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexfin.frontend.ui.screens.components.CustomButton
import com.nexfin.frontend.ui.screens.components.CustomTextField
import com.nexfin.frontend.ui.screens.components.LoadingIndicator

@Composable
fun TopUpScreen(
    amount: String,
    reference: String,
    isLoading: Boolean,
    onAmountChange: (String) -> Unit,
    onReferenceChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit
) {
    FormShell(
        title = "เติมเงินเข้าวอลเล็ท",
        subtitle = "เติมเงินเข้าบัญชีทันที พร้อมระบุบันทึกช่วยจำเพื่อตรวจสอบภายหลัง",
        onBackClick = onBack
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            CustomTextField(amount, onAmountChange, "จำนวนเงิน (THB)")
            CustomTextField(reference, onReferenceChange, "บันทึกช่วยจำ (ไม่บังคับ)")
            CustomButton(
                text = if (isLoading) "กำลังดำเนินการ..." else "ยืนยันการเติมเงิน",
                onClick = onSubmit,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
            if (isLoading) LoadingIndicator("กำลังเติมเงินเข้าวอลเล็ท")
        }
    }
}