package com.ulises.login.user.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ulises.theme.LavenderGlow
import com.ulises.theme.StarGold

@Composable
internal fun StarDecoration() {
    val stars = listOf(
        Triple(10.dp, 8.dp, 10.dp),
        Triple(112.dp, 16.dp, 8.dp),
        Triple(98.dp, 4.dp, 6.dp),
        Triple(20.dp, 52.dp, 6.dp),
    )
    stars.forEachIndexed { i, (x, y, size) ->
        Box(
            modifier = Modifier
                .absoluteOffset(x = x, y = y)
                .size(size)
                .clip(RoundedCornerShape(1.dp))
                .background(
                    if (i % 2 == 0) StarGold.copy(alpha = 0.6f)
                    else LavenderGlow.copy(alpha = 0.45f)
                ),
        )
    }
}