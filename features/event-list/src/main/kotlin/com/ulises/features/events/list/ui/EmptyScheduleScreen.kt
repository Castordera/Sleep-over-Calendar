package com.ulises.features.events.list.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.features.events.list.R
import com.ulises.theme.SleepScheduleTheme

@Composable
internal fun EmptyScheduleScreen(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.svg_sleeping_dog),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
            Text(
                text = stringResource(id = R.string.empty_schedule_title),
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Prev_EmptyScheduleScreen() {
    SleepScheduleTheme {
        Surface {
            EmptyScheduleScreen()
        }
    }
}