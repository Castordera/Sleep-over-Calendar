package com.ulises.session

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import models.User
import javax.inject.Inject

class UserSessionManager @Inject constructor() {

    //  Todo (Inject this)
    private val scope =
        CoroutineScope(SupervisorJob() + CoroutineName("User Session") + Dispatchers.IO)

    private val userSessionSharedFlow = MutableSharedFlow<User?>(replay = 1)
    val userSessionState = userSessionSharedFlow.asSharedFlow()

    //  Emits the user state to all listeners
    fun updateUserSessionState(user: User?) {
        scope.launch { userSessionSharedFlow.emit(user) }
    }
}