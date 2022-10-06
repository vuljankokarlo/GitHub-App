package com.assignment.githubapp.common.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.assignment.githubapp.ui.theme.OpenSansBold_16_24

@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text,
        style = MaterialTheme.typography.OpenSansBold_16_24,
        color = MaterialTheme.colors.primary,
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth()
    )
}