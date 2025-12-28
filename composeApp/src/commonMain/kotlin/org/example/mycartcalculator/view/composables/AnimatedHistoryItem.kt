package org.example.mycartcalculator.view.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable

@Composable
fun AnimatedHistoryItem(
    index: Int,
    content: @Composable () -> Unit
) {
    val enterFromLeft = index % 2 == 0

    AnimatedVisibility(
        visible = true,
        enter = slideInHorizontally(
            initialOffsetX = { fullWidth ->
                if (enterFromLeft) -fullWidth else fullWidth
            },
            animationSpec = tween(
                durationMillis = 600,
                delayMillis = index * 60,
                easing = FastOutSlowInEasing
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = 400,
                delayMillis = index * 60
            )
        )
    ) {
        content()
    }
}
