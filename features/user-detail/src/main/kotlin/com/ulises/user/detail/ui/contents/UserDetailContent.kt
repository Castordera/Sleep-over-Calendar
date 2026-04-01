package com.ulises.user.detail.ui.contents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulises.components.toolbar.AppTopBar
import com.ulises.theme.DangerRed
import com.ulises.theme.LavenderGlow
import com.ulises.theme.NightElevated
import com.ulises.theme.RosePetal
import com.ulises.theme.SleepScheduleTheme
import com.ulises.user.detail.models.UiState
import com.ulises.user.detail.ui.Action
import com.ulises.user.detail.ui.components.ProfileBadge
import models.User

@Composable
internal fun UserDetailContent(
    uiState: UiState,
    onHandleAction: (Action) -> Unit = {},
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Perfil",
                withIcon = false,
                onNavigateBack = { onHandleAction(Action.BackPressed) },
            )
        }
    ) { innerPadding ->
        if (uiState.user == null) return@Scaffold
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(92.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(LavenderGlow, RosePetal)
                        )
                    )
                    .padding(3.dp),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(NightElevated),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = uiState.user.name.first().toString().uppercase(),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 32.sp,
                            color = LavenderGlow,
                        ),
                    )
                }
            }
            Spacer(Modifier.height(14.dp))
            Text(
                text = uiState.user.name,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(3.dp))
            Text(
                text = uiState.user.email,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                ProfileBadge(
                    label = "Admin",
                    color = LavenderGlow,
                    icon = Icons.Rounded.Star,
                )
                ProfileBadge(
                    label = "Google",
                    color = RosePetal,
                    icon = Icons.Rounded.AccountCircle,
                )
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(
                color = NightElevated,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            Spacer(Modifier.height(16.dp))
            Spacer(Modifier.weight(1f))
            //  Logout
            Surface(
                onClick = { onHandleAction(Action.Logout) },
                shape = RoundedCornerShape(14.dp),
                color = DangerRed.copy(alpha = 0.08f),
                border = BorderStroke(1.dp, DangerRed.copy(alpha = 0.22f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(52.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.AutoMirrored.Rounded.Logout,
                        contentDescription = null,
                        tint = DangerRed,
                        modifier = Modifier.size(17.dp),
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Cerrar sesión",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = DangerRed,
                            fontSize = 14.sp,
                        ),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PrevUserDetailContent() {
    SleepScheduleTheme {
        UserDetailContent(
            uiState = UiState(
                user = User(id = "12", email = "email@demos.com", name = "This is my email")
            )
        )
    }
}