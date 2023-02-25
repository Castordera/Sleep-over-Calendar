package com.example.sleepschedule.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sleepschedule.R
import com.example.sleepschedule.ui.theme.SleepScheduleTheme

@Composable
fun EmptyScheduleScreen(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.svg_sleeping_dog),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
            Text(
                text = stringResource(id = R.string.empty_schedule),
                fontSize = 32.sp
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Prev_EmptyScheduleScreen() {
    SleepScheduleTheme {
        EmptyScheduleScreen()
    }
}