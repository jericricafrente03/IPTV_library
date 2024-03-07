package com.bittelasia.theme.presenter.auth

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bittelasia.theme.R
import com.bittelasia.theme.components.AppTextField

@Composable
fun SignupScreen(
    viewModel: SignupViewModel,
    onAuthSuccess: () -> Unit,
    onNavigateToPing: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState.value) {
        SignupUiState.Authenticated -> {
            onAuthSuccess()
        }

        is SignupUiState.Default -> {
            Signup(
                uiState = state,
                onEvent = { event ->
                    viewModel.onEvent(event)
                    if (event is SignupUiEvent.GotoPing) {
                        onNavigateToPing()
                    }
                },
            )
        }
    }
}


@Composable
fun Signup(
    uiState: SignupUiState.Default,
    onEvent: (event: SignupUiEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(.45f),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                SignupLogo(title = R.string.iptv)
                AppTextField(
                    value = uiState.ipAddress,
                    label = R.string.ip_address,
                    hint = "0.0.0.0",
                    onValueChanged = { onEvent(SignupUiEvent.IpChanged(it)) },
                    leadingIcon = Icons.Filled.Place,
                    error = uiState.ipAddressError,
                )

                AppTextField(
                    value = uiState.port,
                    label = R.string.port,
                    hint = "8080",
                    onValueChanged = { onEvent(SignupUiEvent.PortChange(it)) },
                    leadingIcon = Icons.Filled.Lock,
                    error = uiState.portError,
                )

                AppTextField(
                    value = uiState.room,
                    label = R.string.room,
                    hint = "0000",
                    onValueChanged = { onEvent(SignupUiEvent.RoomChanged(it)) },
                    leadingIcon = Icons.Filled.AccountCircle,
                    error = uiState.roomError,
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    uiState.signupError?.let { error ->
                        Text(
                            text = error,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onEvent(SignupUiEvent.Submit)
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_forward),
                        contentDescription = "login",
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                TextButton(
                    onClick = { onEvent(SignupUiEvent.GotoPing) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "DIAGNOSE SERVER CONNECTION",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@Composable
fun SignupLogo(title: Int) {
    Row(
        modifier = Modifier.padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_live_tv_24),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
        )
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = stringResource(title),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 55.sp,
                fontWeight = FontWeight.Light
            )
        )
    }
}
