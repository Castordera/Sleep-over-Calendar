package com.example.sleepschedule.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ulises.session.UserSessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionModule {

    @Provides
    @Singleton
    fun providesFirebaseSession(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun providesSessionManager(): UserSessionManager = UserSessionManager()
}