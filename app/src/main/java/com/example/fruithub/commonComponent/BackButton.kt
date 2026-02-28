package com.example.fruithub.commonComponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruithub.R
import com.example.fruithub.ui.theme.BrandonGrotesque

@Composable
fun BackButton(
    modifier: Modifier = Modifier, onBackClick: () -> Unit
) {
    Surface(
        onClick = onBackClick,
        color = Color.White,
        shape = RoundedCornerShape(25.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.backicon),
                contentDescription = "Back",
                modifier = Modifier.size(16.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Go back",
                fontSize = 14.sp,
                color = Color.Black,
                fontFamily = BrandonGrotesque,
                fontWeight = FontWeight.Normal
            )
        }
    }
}