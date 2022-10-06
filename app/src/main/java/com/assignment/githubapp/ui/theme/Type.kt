package com.assignment.githubapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.assignment.githubapp.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    // for subtext
    body2 = TextStyle(
        fontFamily = FontFamily(Font(R.font.opensans_regular)),
        fontSize = 21.sp
    ),
    // for label
    subtitle1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.opensans_regular)),
        fontSize = 11.sp,
        letterSpacing = 2.sp
    ),
    // for input field text
    subtitle2 = TextStyle(
        fontFamily = FontFamily(Font(R.font.opensans_bold)),
        fontSize = 16.sp,
    )
)

val Typography.OpenSansBold_16_24: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = FontFamily(Font(R.font.opensans_bold)),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = MaterialTheme.colors.primary
        )
    }


val Typography.OpenSansRegular_16_24: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = FontFamily(Font(R.font.opensans_regular)),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = MaterialTheme.colors.primary
        )
    }


val Typography.OpenSansRegular_14_20: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = FontFamily(Font(R.font.opensans_regular)),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = MaterialTheme.colors.primary
        )
    }

val Typography.OpenSansRegular_12_16: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = FontFamily(Font(R.font.opensans_regular)),
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            color = MaterialTheme.colors.primary
        )
    }

val Typography.OpenSansRegular_10_14: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = FontFamily(Font(R.font.opensans_regular)),
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            lineHeight = 14.sp,
            color = MaterialTheme.colors.primary
        )
    }