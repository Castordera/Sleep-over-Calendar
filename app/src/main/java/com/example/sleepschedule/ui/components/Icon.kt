package com.example.sleepschedule.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.sleepschedule.R
import com.ulises.theme.Shapes
import com.ulises.theme.SleepScheduleTheme

@Composable
fun IconButton(
    @DrawableRes imageResource: Int,
    onClick: () -> Unit
) {
    Icon(
        painter = painterResource(id = imageResource),
        contentDescription = null,
        modifier = Modifier
            .clip(Shapes.small)
            .clickable { onClick() }
    )
}

@Preview
@Composable
fun Prev_IconButton() {
    SleepScheduleTheme {
        IconButton(R.drawable.ic_edit) {}
    }
}