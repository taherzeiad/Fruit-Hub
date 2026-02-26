package com.example.fruithub.commonComponent

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fruithub.ui.theme.SecondaryColor

@Composable
fun ButtonOrange(
    onClick: () -> Unit, modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor),
        shape = RoundedCornerShape(10.dp),
        content = content
    )
}