package com.assignment.githubapp.common.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.assignment.githubapp.ui.theme.OpenSansBold_14_20
import com.assignment.githubapp.ui.theme.OpenSansRegular_14_20

@Composable
fun InfoItem(
    label: String,
    data: String
) {
    Row(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.OpenSansBold_14_20,
            color = MaterialTheme.colors.primary
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = data,
            style = MaterialTheme.typography.OpenSansRegular_14_20,
            color = MaterialTheme.colors.primary
        )
    }
}