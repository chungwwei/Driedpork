package com.example.driedpork.composable

import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingSpinner() {
    CircularProgressIndicator(
        modifier = Modifier.width(32.dp), color = MaterialTheme.colors.primary
    )
}