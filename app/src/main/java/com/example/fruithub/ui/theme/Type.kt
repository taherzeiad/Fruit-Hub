package com.example.fruithub.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.fruithub.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val BrandonGrotesque = FontFamily(
    // الخط العادي للنصوص الطويلة والشرح
    Font(R.font.brandongrotesqueregular, FontWeight.Normal),

    // للأسماء والعناوين الفرعية (مثل أسماء السلاطات)
    Font(R.font.brandongrotesquemedium, FontWeight.Medium),

    // للعناوين الرئيسية (مثل "My Basket" أو "Total")
    Font(R.font.brandongrotesquebold, FontWeight.Bold),

    // للعناوين الضخمة جداً أو الأرقام التي تريد إبرازها بشدة
    Font(R.font.brandongrotesqueblack, FontWeight.Black),

    // للنصوص الثانوية جداً أو الملاحظات البسيطة
    Font(R.font.brandongrotesquelight, FontWeight.Light)
)