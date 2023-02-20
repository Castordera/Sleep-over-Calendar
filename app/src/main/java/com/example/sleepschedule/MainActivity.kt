package com.example.sleepschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.sleepschedule.ui.navigation.AppNavHost
import com.example.sleepschedule.ui.navigation.NavDestinations
import com.example.sleepschedule.ui.theme.SleepScheduleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SleepScheduleTheme {
                AppNavHost(startDestination = NavDestinations.HOME)
            }
        }
    }
}