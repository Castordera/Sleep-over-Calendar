package com.example.sleepschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.sleepschedule.ui.navigation.MainScreensNavigation
import com.ulises.theme.SleepScheduleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SleepScheduleTheme {
                MainScreensNavigation()
            }
        }
    }
}