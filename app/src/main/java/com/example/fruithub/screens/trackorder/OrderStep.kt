package com.example.fruithub.screens.trackorder

import androidx.compose.ui.graphics.Color

sealed class TimelineStepType {
    object Standard : TimelineStepType()
    object DeliveryWithCall : TimelineStepType()
    object FinalWithDots : TimelineStepType()
}

data class OrderStep(
    val title: String,
    val subtitle: String? = null,
    val iconRes: Any,
    val iconBg: Color,
    val isCompleted: Boolean,
    val threshold: Float,
    val type: TimelineStepType = TimelineStepType.Standard
)