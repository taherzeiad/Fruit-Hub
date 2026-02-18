package com.example.fruithub.commonComponent

import android.R
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fruithub.ui.theme.OrangePrimary

@Composable
fun ButtonOrange(
    onClick: () -> Unit, modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
        shape = RoundedCornerShape(10.dp),
        content = content
    )
}