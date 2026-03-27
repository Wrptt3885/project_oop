// Frontend/src/commonMain/kotlin/com/nexfin/frontend/ui/screens/transaction/TransferScreen.kt
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
fun TransferScreen(
    toUserId: String,
    amount: String,
    reference: String,
    isLoading: Boolean,
    onToUserIdChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onReferenceChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit
) {
    FormShell(
        title = "โอนเงิน",
        subtitle = "โอนเงินผ่านรหัสผู้ใช้ (User ID) ของผู้รับ ปลอดภัยและตรวจสอบได้",
        onBackClick = onBack
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            CustomTextField(toUserId, onToUserIdChange, "รหัสผู้ใช้ (User ID) ของผู้รับ")
            CustomTextField(amount, onAmountChange, "จำนวนเงิน (THB)")
            CustomTextField(reference, onReferenceChange, "บันทึกช่วยจำ (ไม่บังคับ)")
            CustomButton(
                text = if (isLoading) "กำลังโอนเงิน..." else "ยืนยันการโอนเงิน",
                onClick = onSubmit,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
            if (isLoading) LoadingIndicator("กำลังโอนเงินไปยังผู้รับ")
        }
    }
}