package com.example.sleepschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import com.example.sleepschedule.ui.screens.MainScheduleScreen
import com.example.sleepschedule.ui.theme.SleepScheduleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SleepScheduleTheme {
                Scaffold {
                    MainScheduleScreen(
                        modifier = Modifier.padding(paddingValues = it)
                    )
                }
            }
        }
    }
}