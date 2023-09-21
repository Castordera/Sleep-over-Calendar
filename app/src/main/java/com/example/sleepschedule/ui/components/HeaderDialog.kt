package com.example.sleepschedule.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sleepschedule.R
import com.ulises.theme.Shapes

@Composable
fun HeaderDialog(
    onClickIcon: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = null,
            modifier = Modifier
                .size(26.dp)
                .clip(Shapes.medium)
                .clickable { onClickIcon() }
        )
    }
}