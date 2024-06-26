package com.ulises.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ulises.theme.Shapes
import com.ulises.theme.SleepScheduleTheme

@Composable
fun FabButton(
    @DrawableRes icon: Int,
    contentDescription: String = "",
    onClick: () -> Unit = {},
) {
    FloatingActionButton(
        onClick = onClick,
        shape = Shapes.small,
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = contentDescription
        )
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

@Preview
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevFabButton() {
    SleepScheduleTheme {
        Column {
            FabButton(
                icon = R.drawable.ic_add,
                contentDescription = "Content Description",
            )
        }
    }
}

@Preview
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Prev_RatingButton() {
    SleepScheduleTheme {
        Column {
            RatingButton(R.drawable.ic_thumbs_up_down) {}
            RatingButton(
                image = R.drawable.ic_thumbs_up_down,
                isSelected = true
            ) {}
        }
    }
}