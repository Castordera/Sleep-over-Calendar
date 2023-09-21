package com.example.sleepschedule.ui.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sleepschedule.R
import com.ulises.theme.Shapes
import com.ulises.theme.SleepScheduleTheme

@Composable
fun FAButton(
    onClickButton: () -> Unit
) {
    FloatingActionButton(onClick = { onClickButton() }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = null
        )
    }
}

@Composable
fun ButtonIcon(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    onClick: () -> Unit
) {
    Icon(
        painter = painterResource(id = image),
        contentDescription = null,
        modifier = modifier
            .size(25.dp)
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(2.dp)
    )
}

@Composable
fun RatingButton(
    @DrawableRes image: Int,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val animatedSize by animateDpAsState(targetValue = if (isSelected) 60.dp else 30.dp)

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
fun Prev_FAButton() {
    SleepScheduleTheme {
        Column {
            FAButton {}
            ButtonIcon(image = R.drawable.ic_thumbs_up_down) {

            }
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