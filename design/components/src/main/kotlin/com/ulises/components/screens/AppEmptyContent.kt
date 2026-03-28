package com.ulises.components.screens

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.NightlightRound
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.theme.LavenderGlow
import com.ulises.theme.NightDeep
import com.ulises.theme.NightElevated
import com.ulises.theme.RosePetal
import com.ulises.theme.SleepScheduleTheme
import com.ulises.theme.StarGold
import com.ulises.theme.TextMuted

@Composable
fun AppEmptyContent(
    selectedYear: String,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        MoonIllustration()
        Spacer(Modifier.height(28.dp))
        Text(
            text = "Ninguna noche aún",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Todavía no hay pijamadas en $selectedYear.\n¡Agrega la primera noche y empieza el diario!",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = TextMuted,
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onAddClick,
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(
                containerColor = LavenderGlow,
                contentColor = NightDeep,
            ),
            modifier = Modifier.height(46.dp),
        ) {
            Icon(Icons.Rounded.Add, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(6.dp))
            Text(
                "Agregar noche",
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 13.sp),
            )
        }
        Spacer(Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                Icons.Rounded.Info,
                contentDescription = null,
                tint = TextMuted.copy(alpha = 0.5f),
                modifier = Modifier.size(12.dp),
            )
            Text(
                text = "Prueba cambiando el año",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextMuted.copy(alpha = 0.5f),
                    fontSize = 11.sp,
                ),
            )
        }
    }
}

@Composable
private fun MoonIllustration() {
    val pulse = rememberInfiniteTransition(label = "pulse")
    val alpha by pulse.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(2200, easing = EaseInOutSine),
            RepeatMode.Reverse,
        ),
        label = "moonAlpha",
    )

    Box(
        modifier = Modifier.size(110.dp),
        contentAlignment = Alignment.Center,
    ) {
        // Estrella dorada grande — arriba derecha
        StarShape(
            size = 10.dp,
            color = StarGold,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-6).dp, y = 4.dp),
        )
        // Estrella lavanda — arriba izquierda
        StarShape(
            size = 7.dp,
            color = LavenderGlow,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 12.dp, y = 12.dp)
                .alpha(0.6f),
        )
        // Estrella pequeña — derecha centro
        StarShape(
            size = 5.dp,
            color = StarGold,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = (-2).dp)
                .alpha(0.4f),
        )
        // Círculo de fondo (luna)
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(NightElevated)
                .alpha(alpha),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Rounded.NightlightRound,
                contentDescription = null,
                tint = LavenderGlow,
                modifier = Modifier.size(38.dp),
            )
        }

        // Almohadas — abajo
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(width = 28.dp, height = 14.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(LavenderGlow.copy(alpha = 0.15f))
            )
            Box(
                modifier = Modifier
                    .size(width = 28.dp, height = 14.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(RosePetal.copy(alpha = 0.12f))
            )
        }
    }
}

@Composable
private fun StarShape(
    size: Dp,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(size)
            .background(color, shape = RoundedCornerShape(1.dp)),
    )
}

@Composable
@Preview
private fun PreviewEmptyContent() {
    SleepScheduleTheme {
        AppEmptyContent(
            selectedYear = "2023",
            onAddClick = {},
        )
    }
}