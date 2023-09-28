package com.ulises.components.dialogs

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ulises.components.IconButton
import com.ulises.components.R
import com.ulises.theme.SleepScheduleTheme

@Composable
fun HeaderDialog(
    title: String = "",
    onClickIcon: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            maxLines = 1,
        )
        IconButton(
            icon = R.drawable.ic_close,
            onClick = onClickIcon,
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PrevHeaderDialog() {
    SleepScheduleTheme {
        Surface {
            HeaderDialog(
                title = "This is my title based on this",
                onClickIcon = {},
            )
        }
    }
}