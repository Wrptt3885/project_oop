// Frontend/src/commonMain/kotlin/com/nexfin/frontend/ui/theme/Color.kt
package com.nexfin.frontend.ui.theme

import androidx.compose.ui.graphics.Color

object NexFinColors {
    // โทน Premium FinTech สไตล์แอปธนาคารล้ำๆ
    val DarkBackground = Color(0xFF0B1120) // พื้นหลังน้ำเงินเข้มอมดำ ดูมีมิติ
    val DarkSurface = Color(0xFF172033)    // สีกล่องการ์ด สว่างขึ้นมานิดนึงให้แยกออก
    
    val PrimaryAccent = Color(0xFF2563EB)  // สีน้ำเงินหลัก (Blue) ดูน่าเชื่อถือ
    val SecondaryAccent = Color(0xFF0EA5E9) // สีฟ้า (Sky Blue) เข้าคู่กับสีหลัก ไม่กัดกัน
    
    val TextPrimary = Color(0xFFF8FAFC)    // ขาวสว่าง อ่านง่ายสุดๆ
    val TextSecondary = Color(0xFF94A3B8)  // เทาอ่อนๆ สำหรับข้อความที่ไม่สำคัญมาก
    val Danger = Color(0xFFEF4444)
}