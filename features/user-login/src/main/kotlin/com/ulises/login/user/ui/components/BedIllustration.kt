package com.ulises.login.user.ui.components

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NightlightRound
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.theme.LavenderGlow
import com.ulises.theme.NightElevated
import com.ulises.theme.NightRim

@Composable
internal fun BedIllustration() {
    val pulse = rememberInfiniteTransition(label = "pulse")
    val moonAlpha by pulse.animateFloat(
        initialValue = 0.7f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(2000, easing = EaseInOutSine),
            RepeatMode.Reverse,
        ),
        label = "moon",
    )
 
    Box(
        modifier = Modifier.size(width = 140.dp, height = 120.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        // Estrellas decorativas
        StarDecoration()
 
        // Burbuja luna — arriba izquierda
        Box(
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.TopStart)
                .offset(x = 16.dp)
                .clip(CircleShape)
                .background(NightElevated)
                .border(1.5.dp, NightRim, CircleShape)
                .alpha(moonAlpha),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Rounded.NightlightRound,
                contentDescription = null,
                tint = LavenderGlow,
                modifier = Modifier.size(20.dp),
            )
        }
 
        // ZZZ letras
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 62.dp, y = 6.dp),
            verticalArrangement = Arrangement.spacedBy((-2).dp),
        ) {
            listOf(10.sp to 1f, 8.sp to 0.55f, 6.sp to 0.3f).forEachIndexed { i, (size, alpha) ->
                Text(
                    text = "z",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = LavenderGlow.copy(alpha = alpha),
                        fontSize = size,
                    ),
                    modifier = Modifier
                        .offset(x = (i * 5).dp)
                        .alpha(alpha),
                )
            }
        }
 
        // Cama
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-4).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Almohada
            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .offset(x = (-8).dp)
                    .size(width = 28.dp, height = 16.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(Color(0xFF9DE0E0)),
            )
            // Colcha
            Box(
                modifier = Modifier
                    .size(width = 88.dp, height = 22.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 3.dp, bottomEnd = 3.dp))
                    .background(Color(0xFF7C5CBF)),
            )
            // Colchón
            Box(
                modifier = Modifier
                    .size(width = 102.dp, height = 20.dp)
                    .background(Color(0xFF4FC3C3)),
            )
            // Base
            Box(
                modifier = Modifier
                    .size(width = 102.dp, height = 26.dp)
                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                    .background(Color(0xFF2D2060)),
            )
            // Patas
            Row(
                modifier = Modifier.width(88.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                repeat(2) {
                    Box(
                        modifier = Modifier
                            .size(width = 9.dp, height = 10.dp)
                            .clip(RoundedCornerShape(bottomStart = 3.dp, bottomEnd = 3.dp))
                            .background(Color(0xFF1C1530)),
                    )
                }
            }
        }
    }
}