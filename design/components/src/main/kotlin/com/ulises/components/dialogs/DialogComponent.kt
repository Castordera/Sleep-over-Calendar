package com.ulises.components.dialogs

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.ulises.theme.SleepScheduleTheme

@Composable
fun AlertDialog(
    title: String = "",
    onDismiss: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                HeaderDialog(
                    title = title,
                    onClickIcon = onDismiss
                )
                content()
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrevAlertDialog() {
    SleepScheduleTheme {
        AlertDialog(title = "This is my title") {
            Text("Content text to fill space")
        }
    }
}