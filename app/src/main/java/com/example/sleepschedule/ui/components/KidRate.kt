package com.example.sleepschedule.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ulises.theme.SleepScheduleTheme
import com.example.sleepschedule.ui.utils.RatingType

@Composable
fun KidRate(
    name: String,
    rating: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = name)
        Image(
            painter = painterResource(id = RatingType.fromIntRating(rating).icon),
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )
    }
}

@Preview
@Composable
fun PrevKidRate() {
    SleepScheduleTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            KidRate(
                name = "Renata",
                rating = 0
            )
            KidRate(
                name = "Renata",
                rating = 1
            )
            KidRate(
                name = "Renata",
                rating = -1
            )
        }
    }
}