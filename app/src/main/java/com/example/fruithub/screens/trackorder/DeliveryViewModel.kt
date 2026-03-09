package com.example.fruithub.screens.trackorder

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fruithub.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.graphics.Color

class DeliveryViewModel : ViewModel() {

    var animationProgress = mutableStateOf(0f)
        private set

    // بيانات الخطوات (نفس الترتيب والبيانات في كودك)
    val steps = listOf(
        OrderStep("Order Taken", null, R.drawable.takenorder, Color(0xFFFFFAEB), true, 0.50f),
        OrderStep(
            "Order Is Being Prepared",
            null,
            R.drawable.removebgpreview,
            Color(0xFFF3F4F9),
            true,
            0.60f
        ),
        OrderStep(
            "Order Is Being Delivered",
            "Your delivery agent is coming",
            R.drawable.deliveryman,
            Color(0xFFFFF2F2),
            false,
            0.70f,
            TimelineStepType.DeliveryWithCall
        ),
        OrderStep(
            "Order Received",
            null,
            Icons.Default.Check,
            Color(0xFFE0FFE5),
            false,
            0.90f,
            TimelineStepType.FinalWithDots
        )
    )

    fun updateProgress(value: Float) {
        animationProgress.value = value
    }
}