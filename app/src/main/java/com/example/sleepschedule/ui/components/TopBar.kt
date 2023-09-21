package com.example.sleepschedule.ui.components

import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.sleepschedule.R
import com.ulises.theme.SleepScheduleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String = "",
    onBackClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (onBackClick == null) Unit
            else {
                IconButton(
                    icon = R.drawable.ic_arrow_back,
                    onClick = onBackClick,
                )
            }
        }
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevTopBar() {
    SleepScheduleTheme {
        TopBar(
            title = "This is my title",
            onBackClick = {}
        )
    }
}