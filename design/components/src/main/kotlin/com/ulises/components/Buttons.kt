package com.ulises.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ulises.theme.Shapes
import com.ulises.theme.SleepScheduleTheme

@Composable
fun FabButton(
    @DrawableRes icon: Int,
    contentDescription: String = "",
    onClick: () -> Unit = {},
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = contentDescription
        )
        Text(" Agregar")
    }
}

@Composable
fun RatingButton(
    @DrawableRes image: Int,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val animatedSize by animateDpAsState(
        targetValue = if (isSelected) 60.dp else 30.dp,
        label = "Animated Rating"
    )

    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        modifier = Modifier
            .size(animatedSize)
            .clip(Shapes.medium)
            .clickable { onClick() }
    )
}

@Composable
fun ImageButton(
    modifier: Modifier = Modifier,
    trailIcon: ImageVector,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    OutlinedButton(onClick, modifier = modifier, enabled = enabled) {
        Icon(trailIcon, null)
        Text(text)
    }
}

@PreviewLightDark
@Composable
fun PrevButtons() {
    SleepScheduleTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                FabButton(
                    icon = R.drawable.ic_add,
                    contentDescription = "Content Description",
                )
                RatingButton(R.drawable.ic_thumbs_up_down) {}
                ImageButton(
                    trailIcon = Icons.Default.Add,
                    text = "Text Button",
                    onClick = {},
                )
            }
        }
    }
}