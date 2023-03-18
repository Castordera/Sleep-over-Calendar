package com.example.sleepschedule.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DatabaseUrl

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class FirebaseEventsReference

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class FirebaseUsersReference